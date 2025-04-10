package ru.varno.CaloriesAPI.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.varno.CaloriesAPI.dto.EatDTO;
import ru.varno.CaloriesAPI.dto.ErrorDTO;
import ru.varno.CaloriesAPI.exceptions.DishNotFoundException;
import ru.varno.CaloriesAPI.exceptions.UserNotFoundException;
import ru.varno.CaloriesAPI.service.EatService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eat")
public class EatController {

    private final EatService eatService;

    @PostMapping
    public ResponseEntity<?> saveEat(@Valid @RequestBody EatDTO eatDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(ErrorDTO.builder()
                            .status(400)
                            .message(bindingResult.getAllErrors().getFirst().getDefaultMessage())
                            .build());
        }
        return ResponseEntity.ok(EatDTO.toDTO(eatService.save(EatDTO.toEntity(eatDTO))));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handle(DishNotFoundException e) {
        return ResponseEntity.badRequest()
                .body(ErrorDTO.builder()
                        .status(400)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handle(UserNotFoundException e) {
        return ResponseEntity.badRequest()
                .body(ErrorDTO.builder()
                        .status(400)
                        .message(e.getMessage())
                        .build());
    }

}
