package ru.job4j.accident.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.accident.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentRepository extends CrudRepository<Accident, Integer> {
    @Override
    @Query("select distinct a from Accident a join fetch a.rules ")
    List<Accident> findAll();

    @Override
    @Query("select a from Accident a join fetch a.rules where a.id = :Id")
    Optional<Accident> findById(@Param("Id") Integer id);
}
