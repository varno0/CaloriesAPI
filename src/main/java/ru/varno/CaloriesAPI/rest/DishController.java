package ru.varno.CaloriesAPI.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.varno.CaloriesAPI.dto.DishDTO;
import ru.varno.CaloriesAPI.dto.ErrorDTO;
import ru.varno.CaloriesAPI.exceptions.DishAlreadyExistsException;
import ru.varno.CaloriesAPI.service.DishService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dish")
public class DishController {

    private final DishService dishService;

    @PostMapping
    public ResponseEntity<?> saveDish(@Valid @RequestBody DishDTO dish, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(ErrorDTO.builder()
                            .status(400)
                            .message(bindingResult.getAllErrors().getFirst().getDefaultMessage())
                            .build());
        }
        return ResponseEntity.ok(DishDTO.toDTO(dishService.save(DishDTO.toEntity(dish))));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleException(DishAlreadyExistsException e) {
        return ResponseEntity.badRequest()
                .body(ErrorDTO.builder()
                        .status(400)
                        .message(e.getMessage())
                        .build());
    }
}
