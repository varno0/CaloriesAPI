package ru.varno.CaloriesAPI.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.varno.CaloriesAPI.exceptions.DishNotFoundException;
import ru.varno.CaloriesAPI.exceptions.UserNotFoundException;
import ru.varno.CaloriesAPI.models.Eat;
import ru.varno.CaloriesAPI.repositories.ClientRepositories;
import ru.varno.CaloriesAPI.repositories.DishRepositories;
import ru.varno.CaloriesAPI.repositories.EatRepositories;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EatService {

    private final EatRepositories eatRepositories;

    private final DishRepositories dishRepositories;

    private final ClientRepositories clientRepositories;

    @Transactional
    public Eat save(Eat eat) {
        if (clientRepositories.existsById(eat.getClient().getId()))
            if (!eat.getDishes().stream().map(item ->
                    dishRepositories.existsById(item.getId())).toList().contains(false))
                return eatRepositories.save(eat);
            else throw new DishNotFoundException("Some dish id not exist");
        else throw new UserNotFoundException("Client with id " + eat.getClient().getId() + " not found");
    }
}
