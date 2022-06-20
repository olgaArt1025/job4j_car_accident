package ru.job4j.accident.repository;

import ru.job4j.accident.model.Accident;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.AccidentType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final AtomicInteger number = new AtomicInteger();
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final List<AccidentType> types = new ArrayList<>();

    private AccidentMem() {
    types.add(AccidentType.of(1, "Две машины"));
    types.add(AccidentType.of(2, "Машина и человек"));
    types.add(AccidentType.of(3, "Машина и велосипед"));
    accidents.put(1, new Accident(1, "Т253IU", "Text1", "City1", types.get(0)));
    accidents.put(2, new Accident(2, "Y186LH", "Text2", "City2", types.get(1)));
    accidents.put(3, new Accident(3, "S224YT", "Text3", "City3", types.get(2)));
}

    public Collection<Accident> findAll() {
       return accidents.values();
    }

    public Collection<AccidentType> findAllType() {
        return types;
    }

    public void create(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(number.incrementAndGet());
        }
    AccidentType type = types.get(accident.getType().getId() - 1);
    accident.setType(type);
    accidents.put(accident.getId(), accident);
    }

    public Accident findById(Integer id) {
        return accidents.get(id);
    }

    public void update(Accident accident) {
        AccidentType type = types.get(accident.getType().getId() - 1);
        accident.setType(type);
        accidents.replace(accident.getId(), accident);
    }
}
