package ru.varno.CaloriesAPI.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "dish")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer calories;
    private Integer protein;
    private Integer fats;
    private Integer carbohydrates;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "eats_dishes",
            joinColumns = @JoinColumn(name = "dish_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "eat_id", referencedColumnName = "id")
    )
    private List<Eat> eats;

}
