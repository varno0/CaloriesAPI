package ru.varno.CaloriesAPI.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.varno.CaloriesAPI.models.Client;
import ru.varno.CaloriesAPI.models.Dish;
import ru.varno.CaloriesAPI.models.Eat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EatDTO {

    @NotNull(message = "Client id should be not empty")
    private Long client_id;

    private String timestamp;

    @Size(min = 1, message = "You must add at least 1 dish")
    private List<Long> dishes_id;


    public static Eat toEntity(EatDTO dto) {
        return Eat.builder()
                //
                .client(new Client(dto.client_id))
                .timestamp(dto.timestamp == null ?
                        Timestamp.valueOf(LocalDateTime.now()) :
                        Timestamp.valueOf(dto.timestamp))
                .dishes(dto.dishes_id.stream()
                        .map(i -> Dish.builder()
                                .id(i)
                                .build()).collect(Collectors.toList()))
                .build();
    }

    public static EatDTO toDTO(Eat eat) {
        return EatDTO.builder()
                .client_id(eat.getClient().getId())
                .timestamp(String.valueOf(eat.getTimestamp()))
                .dishes_id(eat.getDishes().stream().map(Dish::getId).collect(Collectors.toList()))
                .build();
    }

}

