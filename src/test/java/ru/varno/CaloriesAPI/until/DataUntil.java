package ru.varno.CaloriesAPI.until;

import ru.varno.CaloriesAPI.models.Client;
import ru.varno.CaloriesAPI.models.Dish;
import ru.varno.CaloriesAPI.models.Eat;
import ru.varno.CaloriesAPI.models.Purpose;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class DataUntil {

    public static Client getClientPersistent() {
        return Client.builder()
                .id(1L)
                .name("Jahn")
                .age(22)
                .email("janh@gmail.com")
                .height(123)
                .purpose(Purpose.WeightGain)
                .weight(234)
                .build();
    }

    public static Dish getDishPersistent() {
        return Dish.builder()
                .id(1L)
                .name("Pasta")
                .fats(22)
                .calories(22)
                .protein(22)
                .carbohydrates(22)
                .build();
    }

    public static Client getClientTransient() {
        return Client.builder()
                .name("Jahn")
                .age(22)
                .email("janh@gmail.com")
                .height(123)
                .purpose(Purpose.WeightGain)
                .weight(234)
                .build();
    }

    public static Dish getDishTransient() {
        return Dish.builder()
                .name("Pasta")
                .fats(22)
                .calories(22)
                .protein(22)
                .carbohydrates(22)
                .build();
    }

    public static Eat getEatPersistent() {
        return Eat.builder()
                .id(1L)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .dishes(List.of(getDishPersistent()))
                .client(getClientPersistent())
                .build();
    }

    public static Eat getEatTransient() {
        return Eat.builder()
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .dishes(List.of(getDishPersistent()))
                .client(getClientPersistent())
                .build();
    }
}
