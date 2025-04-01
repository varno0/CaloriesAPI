package ru.varno.CaloriesAPI.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyReportDTO {

    private Double calories;
    private Integer countEat;
    private List<EatDTO> eatsDTO;

    private LocalDateTime day;
}
