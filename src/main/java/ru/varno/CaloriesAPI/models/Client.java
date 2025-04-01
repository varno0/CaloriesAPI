package ru.varno.CaloriesAPI.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private Integer weight;
    private Integer height;
    @Enumerated(EnumType.STRING)
    private Purpose purpose;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private List<Eat> eat;

    public Client(Long id) {
        this.id = id;
    }

    public Double getCaloriesIntake() {
        return 88.36 + (13.4 * weight) + (4.8 * height) - (5.7 * age);
    }

}
