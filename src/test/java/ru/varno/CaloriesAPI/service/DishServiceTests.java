package ru.varno.CaloriesAPI.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.varno.CaloriesAPI.exceptions.DishAlreadyExistsException;
import ru.varno.CaloriesAPI.models.Dish;
import ru.varno.CaloriesAPI.repositories.DishRepositories;
import ru.varno.CaloriesAPI.until.DataUntil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DishServiceTests {

    @Mock
    private DishRepositories dishRepositories;

    @InjectMocks
    private DishService dishService;

    @Test
    @DisplayName("Test save new dish functionality")
    public void givenDishToSave_whenSaveNewDish_thenDishShouldBeSaved() {
        Dish dish = DataUntil.getDish();
        dish.setId(1L);

        BDDMockito.given(dishRepositories.existsByName(dish.getName())).willReturn(false);
        BDDMockito.given(dishRepositories.save(dish)).willReturn(dish);
        Dish savedDish = dishService.save(dish);

        assertThat(savedDish.getId()).isEqualTo(1L);
        assertThat(savedDish.getName()).isEqualTo(dish.getName());
    }

    @Test
    @DisplayName("Test save dish with duplicate name functionality")
    public void givenDishWithDuplicateName_whenSaveNewDish_thenThrowException() {
        BDDMockito.given(dishRepositories.existsByName(anyString())).willReturn(true);

        assertThrows(DishAlreadyExistsException.class, () -> dishService.save(DataUntil.getDish()));

        verify(dishRepositories, never()).save(any(Dish.class));
    }
}
