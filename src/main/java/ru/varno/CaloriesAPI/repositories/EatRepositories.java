package ru.varno.CaloriesAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.varno.CaloriesAPI.models.Eat;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface EatRepositories extends JpaRepository<Eat, Long> {

    List<Eat> findAllByTimestampBetweenAndClient_Id(Timestamp timestamp, Timestamp timestamp2, Long id);

    @Query("SELECT min(timestamp) FROM Eat e")
    Timestamp findMinTimestamp(Long id);
}
