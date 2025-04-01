package ru.varno.CaloriesAPI.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.varno.CaloriesAPI.dto.EatDTO;
import ru.varno.CaloriesAPI.service.EatService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eat")
public class EatController {

    private final EatService eatService;

    @PostMapping
    public ResponseEntity<EatDTO> saveEat(@RequestBody EatDTO eatDTO) {
        return ResponseEntity.ok(EatDTO.toDTO(eatService.save(EatDTO.toEntity(eatDTO))));
    }
}
