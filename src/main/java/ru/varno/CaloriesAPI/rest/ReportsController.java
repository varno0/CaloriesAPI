package ru.varno.CaloriesAPI.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.varno.CaloriesAPI.dto.DailyReportDTO;
import ru.varno.CaloriesAPI.service.ReportsService;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportsController {

    private final ReportsService reportsService;

    @GetMapping("/daily")
    public ResponseEntity<?> getDailyReport(@RequestParam(required = false) String timestamp,
                                            @RequestParam Long id) {
        if (timestamp == null || timestamp.isEmpty()) {
            timestamp = Instant.now().toString();
        }
        return ResponseEntity.ok(reportsService.getDailyReport(timestamp, id));
    }

    @GetMapping("/caloriesIntake")
    public ResponseEntity<?> getCaloriesIntakeReport(@RequestParam Long id) {
        return ResponseEntity.ok(reportsService.getCaloriesIntakeReport(id));
    }

    @GetMapping("/getAllDailyReports")
    public ResponseEntity<?> getAllDailyReports(@RequestParam Long id) {
        List<DailyReportDTO> reports = reportsService.getAllDailyReports(id);


        return ResponseEntity.ok(reports);
    }
}
