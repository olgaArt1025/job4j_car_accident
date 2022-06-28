package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentHibernate;

import java.util.Collection;

@Service
public class AccidentService {
    private  final AccidentHibernate store;

    public AccidentService(AccidentHibernate store) {
        this.store = store;
    }

    public Collection<Accident> findAll() {
        return store.getAll();
    }

    public Collection<AccidentType> findAllType() {
        return store.findAllType();
    }

    public Collection<Rule> findAllRule() {
        return store.findAllRule();
    }

    public void create(Accident accident) {
        store.create(accident);
    }

    public Accident findById(Integer id) {
       return store.findById(id);
    }

    public Rule findByIdRule(Integer id) {
        return store.findByIdRule(id);
    }

    public void update(Accident accident) {
        store.update(accident);
    }
}
