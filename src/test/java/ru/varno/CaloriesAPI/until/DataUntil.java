package ru.varno.CaloriesAPI.until;

import ru.varno.CaloriesAPI.models.Client;
import ru.varno.CaloriesAPI.models.Dish;
import ru.varno.CaloriesAPI.models.Purpose;

public class DataUntil {

    public static Client getClient() {
        return Client.builder()
                .name("Jahn")
                .age(22)
                .email("janh@gmail.com")
                .height(123)
                .purpose(Purpose.WeightGain)
                .weight(234)
                .build();
    }

    public static Dish getDish() {
        return Dish.builder()
                .name("Pasta")
                .fats(22)
                .calories(22)
                .protein(22)
                .carbohydrates(22)
                .build();
    }
}
