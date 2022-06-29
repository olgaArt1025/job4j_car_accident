package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.AccidentTypeRepository;
import ru.job4j.accident.repository.RuleRepository;

import java.util.Collection;

@Service
public class AccidentService {
    private  final AccidentRepository store;
    private final AccidentTypeRepository typeStore;
    private final RuleRepository ruleStore;

    public AccidentService(AccidentRepository store, AccidentTypeRepository typeStore, RuleRepository ruleStore) {
        this.store = store;
        this.typeStore = typeStore;
        this.ruleStore = ruleStore;
    }

    public Collection<Accident> findAll() {
        return store.findAll();
    }

    public Collection<AccidentType> findAllType() {
        return typeStore.findAll();
    }

    public Collection<Rule> findAllRule() {
        return ruleStore.findAll();
    }

    public void create(Accident accident) {
        store.save(accident);
    }

    public Accident findById(Integer id) {
       return store.findById(id).orElse(null);
    }

    public Rule findByIdRule(Integer id) {
        return ruleStore.findById(id).orElse(null);
    }

    public void update(Accident accident) {
        store.save(accident);
    }
}
