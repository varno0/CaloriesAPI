package ru.varno.CaloriesAPI.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.varno.CaloriesAPI.dto.EatDTO;
import ru.varno.CaloriesAPI.models.Eat;
import ru.varno.CaloriesAPI.service.EatService;
import ru.varno.CaloriesAPI.until.DataUntil;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(EatController.class)
public class EatControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EatService eatService;


    @Test
    @DisplayName("Test save eat functionality")
    public void givenEatDTO_whenSaveEat_thenReturnEatDTO() throws Exception {

        BDDMockito.given(eatService.save(any(Eat.class)))
                .willReturn(DataUntil.getEatPersistent());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/eat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(EatDTO.toDTO(DataUntil.getEatTransient())))
        );

        result
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.client_id").exists());
    }
}
