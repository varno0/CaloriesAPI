package ru.varno.CaloriesAPI.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import ru.varno.CaloriesAPI.dto.CaloriesIntakeReport;
import ru.varno.CaloriesAPI.dto.DailyReportDTO;
import ru.varno.CaloriesAPI.dto.EatDTO;
import ru.varno.CaloriesAPI.exceptions.UserNotFoundException;
import ru.varno.CaloriesAPI.models.Client;
import ru.varno.CaloriesAPI.models.Dish;
import ru.varno.CaloriesAPI.models.Eat;
import ru.varno.CaloriesAPI.repositories.ClientRepositories;
import ru.varno.CaloriesAPI.repositories.EatRepositories;

import java.sql.Timestamp;
import java.time.*;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportsService {

    private final ClientRepositories clientRepositories;
    private final EatRepositories eatRepositories;
    private final Logger log = LoggerFactory.getLogger(ReportsService.class);
    private final JedisPool jedisPool;
    private final ObjectMapper mapper;
    private final long TTL = 60L;


    public DailyReportDTO getDailyReport(String timestamp, Long id) {
        if (!clientRepositories.existsById(id))
            throw new UserNotFoundException("User not found");
        LocalDate day = LocalDate.ofInstant(Instant.parse(timestamp), ZoneId.systemDefault());

        List<Eat> eats = eatRepositories.findAllByTimestampBetweenAndClient_Id(Timestamp.valueOf(day.atStartOfDay()),
                Timestamp.valueOf(day.atTime(LocalTime.MAX)), id);

        return DailyReportDTO.builder()
                .eatsDTO(eats.stream()
                        .map(EatDTO::toDTO)
                        .toList())
                .day(LocalDateTime.of(day, LocalTime.now()))
                .calories(calculateTotalCalories(eats))
                .countEat(eats.size())
                .build();
    }

    public CaloriesIntakeReport getCaloriesIntakeReport(Long id) {
        Client client = clientRepositories.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        Double caloriesConsumed = getDailyReport(Instant.now().toString(), id).getCalories();
        return CaloriesIntakeReport.builder()
                .caloriesIntake(client.getCaloriesIntake())
                .caloriesConsumed(caloriesConsumed)
                .caloriesDifference(client.getCaloriesIntake() - caloriesConsumed)
                .build();
    }

    public List<DailyReportDTO> getAllDailyReports(Long id) {
        if (!clientRepositories.existsById(id))
            throw new UserNotFoundException("User not found");
        Timestamp minTimestamp = eatRepositories.findMinTimestamp(id);
        LocalDate minDate = minTimestamp.toLocalDateTime().toLocalDate();
        return minDate.datesUntil(LocalDate.now()
                .plusDays(1)).map(date ->
                getDailyReport(date.atStartOfDay(ZoneId.systemDefault()).toInstant().toString(), id)
        ).toList();
    }

    public List<DailyReportDTO> getCachedAllDailyReports(Long id) {
        try (Jedis jedis = jedisPool.getResource()) {

            String key = "allDailyReports" + id;
            List<String> raw = jedis.lrange(key, 0, -1);
            if (!raw.isEmpty()) {
                return raw.stream().map(this::deserializer).toList();
            }
            List<DailyReportDTO> reports = getAllDailyReports(id);

            reports.forEach(dailyReport ->
                    jedis.rpush(key, serializer(dailyReport)));
            jedis.expire(key, TTL);
            return reports;
        }
    }


    private DailyReportDTO deserializer(String dailyReport) {
        try {
            return mapper.readValue(dailyReport, DailyReportDTO.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String serializer(DailyReportDTO dailyReportDTO) {
        try {
            return mapper.writeValueAsString(dailyReportDTO);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Double calculateTotalCalories(List<Eat> eats) {
        return eats.stream()
                .flatMap(eat -> eat.getDishes().stream())
                .mapToDouble(Dish::getCalories)
                .sum();
    }

}
