package ru.varno.CaloriesAPI.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.varno.CaloriesAPI.dto.DishDTO;
import ru.varno.CaloriesAPI.service.DishService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dish")
public class DishController {

    private final DishService dishService;

    @PostMapping
    public ResponseEntity<DishDTO> saveDish(@RequestBody DishDTO dish) {
        return ResponseEntity.ok(DishDTO.toDTO(
                dishService.save(
                        DishDTO.toEntity(dish))));
    }
}
