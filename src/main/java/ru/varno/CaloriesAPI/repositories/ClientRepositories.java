package ru.varno.CaloriesAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.varno.CaloriesAPI.models.Client;

@Repository
public interface ClientRepositories extends JpaRepository<Client, Long> {

    boolean existsByEmail(String email);

}
