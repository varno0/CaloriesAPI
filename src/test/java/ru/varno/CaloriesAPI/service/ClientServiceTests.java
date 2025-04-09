package ru.varno.CaloriesAPI.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.varno.CaloriesAPI.exceptions.UserNotFoundException;
import ru.varno.CaloriesAPI.exceptions.UserWithDuplicateEmailException;
import ru.varno.CaloriesAPI.models.Client;
import ru.varno.CaloriesAPI.repositories.ClientRepositories;
import ru.varno.CaloriesAPI.until.DataUntil;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {

    @Mock
    private ClientRepositories clientRepositories;

    @InjectMocks
    private ClientService clientService;


    @Test
    @DisplayName("Test save client functionality")
    public void givenClientToSave_whenSaveClient_thenClientSaved() {
        Client client = DataUntil.getClient();
        client.setId(1L);

        BDDMockito.given(clientRepositories.existsByEmail(anyString()))
                .willReturn(false);
        BDDMockito.given(clientRepositories.save(client))
                .willReturn(client);

        Client savedClient = clientService.save(client);

        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test save client with duplicate email functionality")
    public void givenClient1ToSave_whenSaveClient_thenThrowException() {
        Client client = DataUntil.getClient();

        BDDMockito.given(clientRepositories.existsByEmail(anyString()))
                .willReturn(true);

        assertThrows(UserWithDuplicateEmailException.class, () -> clientService.save(client));

        verify(clientRepositories, never()).save(any(Client.class));
    }

    @Test
    @DisplayName("Test find client bu id functionality")
    public void givenId_whenFindByID_thenClientIsFound() {
        Client client = DataUntil.getClient();
        client.setId(1L);

        BDDMockito.given(clientRepositories.findById(anyLong()))
                .willReturn(Optional.of(client));

        Client foundClient = clientService.findById(1L);

        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Test find client by not exists id functinality")
    public void givenId_whenFindByNotExistId_thenThrowException() {
        BDDMockito.given(clientRepositories.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> clientService.findById(1L));
    }


}
