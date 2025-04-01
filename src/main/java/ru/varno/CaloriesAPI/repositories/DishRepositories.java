package ru.varno.CaloriesAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.varno.CaloriesAPI.models.Dish;

@Repository
public interface DishRepositories extends JpaRepository<Dish, Long> {

}
