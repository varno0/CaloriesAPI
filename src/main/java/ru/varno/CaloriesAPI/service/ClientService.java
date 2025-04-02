package ru.varno.CaloriesAPI.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.varno.CaloriesAPI.exceptions.UserNotFoundException;
import ru.varno.CaloriesAPI.exceptions.UserWithDuplicateEmailException;
import ru.varno.CaloriesAPI.models.Client;
import ru.varno.CaloriesAPI.repositories.ClientRepositories;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService {

    private final ClientRepositories clientRepositories;

    @Transactional
    public Client save(Client client) {
        if (!clientRepositories.existsByEmail(client.getEmail()))
            return clientRepositories.save(client);
        throw new UserWithDuplicateEmailException("Client with email " + client.getEmail() + " exist yet");
    }

    public Client findById(Long id) {
        return clientRepositories.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id " + id + " not found"));
    }


}
