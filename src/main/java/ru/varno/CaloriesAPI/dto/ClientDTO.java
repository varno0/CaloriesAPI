package ru.varno.CaloriesAPI.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.varno.CaloriesAPI.models.Client;
import ru.varno.CaloriesAPI.models.Purpose;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    @Null(message = "Id should be empty!")
    private Long id;
    @NotNull(message = "Name should not be empty!")
    private String name;
    @Email(message = "This is not email!")
    @NotEmpty(message = "Email should be not empty!")
    private String email;
    @NotNull(message = "Age should not be empty!")
    @Min(value = 1, message = "Age should be between 1 and 200")
    @Max(value = 200, message = "Age should be between 1 and 200")
    private Integer age;
    @NotNull(message = "Weight should not be empty!")
    @Min(value = 1, message = "weight should be between 1 and 400")
    @Max(value = 400, message = "weight should be between 1 and 400")
    private Integer weight;
    @NotNull(message = "Height should not be empty!")
    @Min(value = 1, message = "height should be between 1 and 400")
    @Max(value = 400, message = "height should be between 1 and 400")
    private Integer height;
    @NotNull(message = "Purpose should not be empty!")
    private Purpose purpose;

    public static Client toEntity(ClientDTO clientDTO) {
        return Client.builder()
                .id(clientDTO.getId())
                .name(clientDTO.getName())
                .email(clientDTO.getEmail())
                .age(clientDTO.getAge())
                .weight(clientDTO.getWeight())
                .height(clientDTO.getHeight())
                .purpose(clientDTO.getPurpose())
                .build();
    }

    public static ClientDTO toDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .age(client.getAge())
                .weight(client.getWeight())
                .height(client.getHeight())
                .purpose(client.getPurpose())
                .build();
    }
}

