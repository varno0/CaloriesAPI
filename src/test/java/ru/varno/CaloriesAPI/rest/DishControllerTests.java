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
import ru.varno.CaloriesAPI.dto.DishDTO;
import ru.varno.CaloriesAPI.models.Dish;
import ru.varno.CaloriesAPI.service.DishService;
import ru.varno.CaloriesAPI.until.DataUntil;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(DishController.class)
public class DishControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DishService dishService;


    @Test
    @DisplayName("Test save Dish functionality")
    public void givenDishDTO_whenSaveDish_thenResponseIsOk() throws Exception {


        BDDMockito.given(dishService.save(any(Dish.class)))
                .willReturn(DataUntil.getDishPersistent());
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/dish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DishDTO.toDTO(DataUntil.getDishTransient()))));

        result
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }
}
