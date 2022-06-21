package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final AtomicInteger number = new AtomicInteger();
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    public AccidentMem() {
    types.put(1, AccidentType.of(1, "Две машины"));
    types.put(2, AccidentType.of(2, "Машина и человек"));
    types.put(3, AccidentType.of(3, "Машина и велосипед"));
        rules.put(1, Rule.of(1, "Статья. 1"));
        rules.put(2, Rule.of(2, "Статья. 2"));
        rules.put(3, Rule.of(3, "Статья. 3"));
    accidents.put(1, new Accident(1, "Т253IU", "Text1", "City1", types.get(1)));
    accidents.put(2, new Accident(2, "Y186LH", "Text2", "City2", types.get(2)));
    accidents.put(3, new Accident(3, "S224YT", "Text3", "City3", types.get(3)));
}

    public Collection<Accident> findAll() {
       return accidents.values();
    }

    public Collection<AccidentType> findAllType() {
        return types.values();
    }

    public Collection<Rule> findAllRule() {
        return rules.values();
    }

    public void create(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(number.incrementAndGet());
        }

    AccidentType type = types.get(accident.getType().getId());
    accident.setType(type);
    accidents.put(accident.getId(), accident);
    }

    public Accident findById(Integer id) {
        return accidents.get(id);
    }

    public Rule findByIdRule(Integer id) {
        return rules.get(id);
    }

    public void update(Accident accident) {
        AccidentType type = types.get(accident.getType().getId());
        accident.setType(type);
        accidents.replace(accident.getId(), accident);
    }
}
