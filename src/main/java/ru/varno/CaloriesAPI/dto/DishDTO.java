package ru.varno.CaloriesAPI.dto;


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

    private Long id;
    private String name;
    private Integer calories;
    private Integer protein;
    private Integer fats;
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
