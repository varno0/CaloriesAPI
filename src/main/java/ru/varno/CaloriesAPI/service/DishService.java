package ru.varno.CaloriesAPI.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.varno.CaloriesAPI.models.Dish;
import ru.varno.CaloriesAPI.repositories.DishRepositories;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DishService {

    private final DishRepositories dishRepositories;

    @Transactional
    public Dish save(Dish dish) {
        return dishRepositories.save(dish);
    }
}
