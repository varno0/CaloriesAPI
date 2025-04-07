package ru.varno.CaloriesAPI.until;

import ru.varno.CaloriesAPI.models.Client;
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
}
