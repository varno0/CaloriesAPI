package ru.varno.CaloriesAPI.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.varno.CaloriesAPI.dto.ClientDTO;
import ru.varno.CaloriesAPI.service.ClientService;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> saveClient(@Valid @RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(ClientDTO.toDTO(
                clientService.save(
                        ClientDTO.toEntity(clientDTO))));
    }

    @GetMapping
    public ResponseEntity<?> getClientCalories(@RequestParam Long id) {
        return ResponseEntity.ok(clientService.findById(id).getCaloriesIntake());
    }

}
