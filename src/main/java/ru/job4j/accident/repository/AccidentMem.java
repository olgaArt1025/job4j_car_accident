package ru.job4j.accident.repository;

import ru.job4j.accident.model.Accident;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

private AccidentMem() {
accidents.put(1, new Accident(1, "User1", "Text1", "City1"));
    accidents.put(2, new Accident(2, "User2", "Text2", "City2"));
    accidents.put(3, new Accident(3, "User3", "Text3", "City3"));
}

    public Collection<Accident> findAll() {
       return accidents.values();
    }

    public void create(Accident accident) {
    accidents.put(accident.getId(), accident);
    }

    public Accident findById(Integer id) {
        return accidents.get(id);
    }

    public void update(Accident accident) {
        accidents.replace(accident.getId(), accident);
    }
}
