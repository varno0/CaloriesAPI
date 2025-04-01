package ru.varno.CaloriesAPI.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaloriesIntakeReport {
    private Double caloriesIntake;
    private Double caloriesConsumed;
    private Double caloriesDifference;
}
