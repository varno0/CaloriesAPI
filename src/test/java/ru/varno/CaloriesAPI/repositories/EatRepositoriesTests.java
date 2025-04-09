package ru.varno.CaloriesAPI.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.varno.CaloriesAPI.models.Client;
import ru.varno.CaloriesAPI.models.Dish;
import ru.varno.CaloriesAPI.models.Eat;
import ru.varno.CaloriesAPI.until.DataUntil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

        Client client = clientRepositories.save(DataUntil.getClient());
        Dish dish = dishRepositories.save(DataUntil.getDish());
        Eat eat1 = Eat.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .dishes(List.of(dish))
                .client(client)
                .build();
        Eat eat2 = Eat.builder()
                .timestamp(Timestamp.valueOf(LocalDateTime.now().minusDays(1)))
                .dishes(List.of(dish))
                .client(client)
                .build();
        eatRepositories.save(eat1);
        eatRepositories.save(eat2);

        Timestamp timestamp = eatRepositories.findMinTimestamp(1L);

        assertThat(timestamp).isNotNull();
        assertThat(timestamp).isEqualTo(eat2.getTimestamp());

    }
}
