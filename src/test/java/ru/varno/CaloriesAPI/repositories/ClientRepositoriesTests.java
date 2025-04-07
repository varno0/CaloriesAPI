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
        Client client = DataUntil.getClient();
        //when
        Client clientIsSaved = clientRepositories.save(client);
        //then
        assertThat(clientIsSaved).isNotNull();
        assertThat(clientIsSaved.getId()).isNotNull();
    }


}
