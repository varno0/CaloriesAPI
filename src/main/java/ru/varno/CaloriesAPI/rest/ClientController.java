package ru.varno.CaloriesAPI.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.varno.CaloriesAPI.dto.ClientDTO;
import ru.varno.CaloriesAPI.dto.ErrorDTO;
import ru.varno.CaloriesAPI.exceptions.UserNotFoundException;
import ru.varno.CaloriesAPI.exceptions.UserWithDuplicateEmailException;
import ru.varno.CaloriesAPI.service.ClientService;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<?> saveClient(@Valid @RequestBody ClientDTO clientDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(ErrorDTO.builder()
                            .status(400)
                            .message(bindingResult.getAllErrors().getFirst().getDefaultMessage())
                            .build());
        }
        return ResponseEntity.ok(ClientDTO.toDTO(clientService.save(ClientDTO.toEntity(clientDTO))));
    }

    @GetMapping
    public ResponseEntity<?> getClientCalories(@RequestParam Long id) {

        return ResponseEntity.ok(clientService.findById(id).getCaloriesIntake());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleException(UserNotFoundException e) {
        return ResponseEntity.badRequest()
                .body(ErrorDTO.builder()
                        .status(400)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleException(UserWithDuplicateEmailException e) {
        return ResponseEntity.badRequest()
                .body(ErrorDTO.builder()
                        .status(400)
                        .message(e.getMessage())
                        .build());
    }

}
