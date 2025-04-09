package ru.varno.CaloriesAPI.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.varno.CaloriesAPI.models.Dish;
import ru.varno.CaloriesAPI.until.DataUntil;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DishRepositoriesTests {

    @Autowired
    private DishRepositories dishRepositories;

    @BeforeEach
    public void setUp() {
        dishRepositories.deleteAll();
    }

    @Test
    @DisplayName("Test save dish functionality")
    public void givenDishToSave_whenSaveDish_thenSuccess() {
        Dish dishToSave = DataUntil.getDish();
        Dish savedDish = dishRepositories.save(dishToSave);
        assertThat(savedDish).isNotNull();
        assertThat(savedDish.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test exist dish by name")
    public void givenDishName_whenFindDishByName_thenSuccess() {
        String dishName = dishRepositories.save(DataUntil.getDish()).getName();

        boolean exists = dishRepositories.existsByName(dishName);

        assertThat(exists).isTrue();

    }
}
