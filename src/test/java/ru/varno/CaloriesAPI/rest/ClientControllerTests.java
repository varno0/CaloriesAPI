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
import ru.varno.CaloriesAPI.dto.ClientDTO;
import ru.varno.CaloriesAPI.models.Client;
import ru.varno.CaloriesAPI.service.ClientService;
import ru.varno.CaloriesAPI.until.DataUntil;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ClientController.class)
public class ClientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClientService clientService;


    @Test
    @DisplayName("Test save client functionality")
    void givenClientDTO_whenCreateClient_thenReturnDTO() throws Exception {

        BDDMockito.given(clientService.save(any(Client.class)))
                .willReturn(DataUntil.getClientPersistent());

        ResultActions result = mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ClientDTO.toDTO(DataUntil.getClientTransient()))));

        result
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }


    @Test
    @DisplayName("Test get calories of client functionality")
    void givenClientId_whenGetCalories_thenReturnedCalories() throws Exception {

        BDDMockito.given(clientService.findById(anyLong()))
                .willReturn(DataUntil.getClientPersistent());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/client")
                .param("id", Long.toString(DataUntil.getClientPersistent().getId())));

        result
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
