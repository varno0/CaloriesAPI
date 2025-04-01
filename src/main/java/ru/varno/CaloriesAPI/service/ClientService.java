package ru.varno.CaloriesAPI.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.varno.CaloriesAPI.exceptions.UserNotFoundException;
import ru.varno.CaloriesAPI.models.Client;
import ru.varno.CaloriesAPI.repositories.ClientRepositories;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService {

    private final ClientRepositories clientRepositories;

    @Transactional
    public Client save(Client client) {
        return clientRepositories.save(client);
    }

    public Client findById(Long id) {
        return clientRepositories.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with id " + id));
    }


}
