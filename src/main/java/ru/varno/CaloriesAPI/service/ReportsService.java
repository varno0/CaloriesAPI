package ru.varno.CaloriesAPI.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.varno.CaloriesAPI.dto.CaloriesIntakeReport;
import ru.varno.CaloriesAPI.dto.DailyReportDTO;
import ru.varno.CaloriesAPI.dto.EatDTO;
import ru.varno.CaloriesAPI.exceptions.UserNotFoundException;
import ru.varno.CaloriesAPI.models.Client;
import ru.varno.CaloriesAPI.models.Dish;
import ru.varno.CaloriesAPI.models.Eat;
import ru.varno.CaloriesAPI.repositories.ClientRepositories;
import ru.varno.CaloriesAPI.repositories.DishRepositories;
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
    private final DishRepositories dishRepositories;


    public DailyReportDTO getDailyReport(String timestamp, Long id) {
        LocalDate day = LocalDate.ofInstant(Instant.parse(timestamp), ZoneId.systemDefault());

        List<Eat> eats = eatRepositories.findAllByTimestampBetweenAndClient_Id(Timestamp.valueOf(day.atStartOfDay()),
                Timestamp.valueOf(day.atTime(LocalTime.MAX)), id);

        return DailyReportDTO.builder()
                .eatsDTO(eats.stream()
                        .map(EatDTO::toDTO)
                        .toList())
                .day(LocalDateTime.of(day, LocalTime.now()))
                .calories(eats.stream()
                        .flatMap(eat -> eat.getDishes().stream())
                        .mapToDouble(Dish::getCalories)
                        .sum())
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
        Timestamp minTimestamp = eatRepositories.findMinTimestamp(id);
        LocalDate minDate = minTimestamp.toLocalDateTime().toLocalDate();
        return minDate.datesUntil(LocalDate.now().plusDays(1)).map(
                date -> {
                    return getDailyReport(date.atStartOfDay(ZoneId.systemDefault()).toInstant().toString(), id);
                }
        ).toList();
    }


}
