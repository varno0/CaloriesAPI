package ru.varno.CaloriesAPI.repositories;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.varno.CaloriesAPI.models.Client;
import ru.varno.CaloriesAPI.until.DataUntil;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ClientRepositoriesTests {

    @Autowired
    private ClientRepositories clientRepositories;

    @BeforeEach
    void setUp() {
        clientRepositories.deleteAll();
    }

    @Test
    @DisplayName("Test add client functionality")
    public void givenClient_whenSaveClient_thenClientIsSaved() {
        //given
        Client client = DataUntil.getClientTransient();
        //when
        Client clientIsSaved = clientRepositories.save(client);
        //then
        assertThat(clientIsSaved).isNotNull();
        assertThat(clientIsSaved.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test find client by id functionality")
    public void givenClient_whenFindClientById_thenClientIsFound() {
        Long id = clientRepositories.save(DataUntil.getClientTransient()).getId();

        Client client = clientRepositories.findById(id).orElse(null);

        assertThat(client).isNotNull();
        assertThat(client.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Test exist client by id functionality")
    public void givenIdExists_whenFindClientById_thenClientIsFound() {
        Long id = clientRepositories.save(DataUntil.getClientTransient()).getId();

        boolean exists = clientRepositories.existsById(id);

        assertThat(exists).isTrue();
    }


}
