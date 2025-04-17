package ru.varno.CaloriesAPI.repositories;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.env.Environment;
import ru.varno.CaloriesAPI.models.Client;
import ru.varno.CaloriesAPI.models.Dish;
import ru.varno.CaloriesAPI.models.Eat;
import ru.varno.CaloriesAPI.until.DataUntil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
public class EatRepositoriesTests {

    @Autowired
    private EatRepositories eatRepositories;
    @Autowired
    private ClientRepositories clientRepositories;
    @Autowired
    private DishRepositories dishRepositories;

    @BeforeEach
    public void setUp() {
        eatRepositories.deleteAll();
    }

    @Test
    @DisplayName("Test find minimal timestamp functionality")
    public void givenSavedEat_whenFindMinimalTimestamp_thenReturnMinimalTimestamp() {
        Client client = clientRepositories.save(DataUntil.getClientTransient());
        Dish dish = dishRepositories.save(DataUntil.getDishTransient());
        Eat eat1 = DataUntil.getEatTransient();
        eat1.setClient(client);
        eat1.setDishes(List.of(dish));
        Eat eat2 = DataUntil.getEatTransient();
        eat2.setClient(client);
        eat2.setDishes(List.of(dish));
        eat2.setTimestamp(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
        eatRepositories.save(eat1);
        eatRepositories.save(eat2);

        Timestamp timestamp = eatRepositories.findMinTimestamp(client.getId());
        log.info("Minimal timestamp: {}", timestamp);

        assertThat(timestamp).isNotNull();
        assertThat(timestamp).isEqualTo(eat2.getTimestamp());

    }
}
