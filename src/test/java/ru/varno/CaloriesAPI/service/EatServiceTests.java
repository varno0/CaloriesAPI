package ru.varno.CaloriesAPI.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.varno.CaloriesAPI.exceptions.DishNotFoundException;
import ru.varno.CaloriesAPI.exceptions.UserNotFoundException;
import ru.varno.CaloriesAPI.models.Client;
import ru.varno.CaloriesAPI.models.Dish;
import ru.varno.CaloriesAPI.models.Eat;
import ru.varno.CaloriesAPI.repositories.ClientRepositories;
import ru.varno.CaloriesAPI.repositories.DishRepositories;
import ru.varno.CaloriesAPI.repositories.EatRepositories;
import ru.varno.CaloriesAPI.until.DataUntil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EatServiceTests {

    @Mock
    private EatRepositories eatRepositories;
    @Mock
    private DishRepositories dishRepositories;
    @Mock
    private ClientRepositories clientRepositories;

    @InjectMocks
    private EatService eatService;

    @Test
    @DisplayName("Test save Eat functionality")
    public void givenEat_whenSaveEat_thenEatIsSaved() {
        Client client = DataUntil.getClientPersistent();
        client.setId(1L);
        Dish dish = DataUntil.getDishPersistent();
        dish.setId(1L);
        Eat eat = Eat.builder()
                .id(1L)
                .client(client)
                .dishes(List.of(dish))
                .build();

        BDDMockito.given(clientRepositories.existsById(anyLong())).willReturn(true);
        BDDMockito.given(dishRepositories.existsById(anyLong())).willReturn(true);
        BDDMockito.given(eatRepositories.save(any(Eat.class))).willReturn(eat);

        Eat savedEat = eatService.save(eat);

        assertThat(savedEat).isNotNull();
        assertThat(savedEat.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test save Eat with not exist client functionality")
    public void givenEatWithNotExistClient_whenSaveEat_thenThrowException() {

        BDDMockito.given(clientRepositories.existsById(anyLong())).willReturn(false);

        assertThrows(UserNotFoundException.class, () -> eatService.save(DataUntil.getEatTransient()));

        verify(eatRepositories, never()).save(any(Eat.class));
    }

    @Test
    @DisplayName("Test save Eat with not exist dish functionality")
    public void givenEatWithNotExistDish_whenSaveEat_thenThrowException() {

        BDDMockito.given(clientRepositories.existsById(anyLong())).willReturn(true);
        BDDMockito.given(dishRepositories.existsById(anyLong())).willReturn(false);

        assertThrows(DishNotFoundException.class, () -> eatService.save(DataUntil.getEatTransient()));

        verify(eatRepositories, never()).save(any(Eat.class));
    }

}
