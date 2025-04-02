package ru.varno.CaloriesAPI.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.varno.CaloriesAPI.models.Dish;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishDTO {

    @Null
    private Long id;
    @NotNull(message = "Name should be not empty")
    private String name;
    @Positive(message = "Calories should bo positive")
    private Integer calories;
    @Positive(message = "Protein should bo positive")
    private Integer protein;
    @Positive(message = "Fats should bo positive")
    private Integer fats;
    @Positive(message = "Carbohydrates should bo positive")
    private Integer carbohydrates;

    public static DishDTO toDTO(Dish dish) {
        return DishDTO.builder()
                .id(dish.getId())
                .name(dish.getName())
                .calories(dish.getCalories())
                .protein(dish.getProtein())
                .fats(dish.getFats())
                .carbohydrates(dish.getCarbohydrates())
                .build();
    }

    public static Dish toEntity(DishDTO dto) {
        return Dish.builder()
                .id(dto.getId())
                .name(dto.getName())
                .calories(dto.getCalories())
                .protein(dto.getProtein())
                .fats(dto.getFats())
                .carbohydrates(dto.getCarbohydrates())
                .build();
    }
}
