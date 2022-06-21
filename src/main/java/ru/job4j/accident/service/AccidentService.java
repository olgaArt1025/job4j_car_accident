package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentMem;

import java.util.Collection;

@Service
public class AccidentService {
private  final AccidentMem accidentMem;

    public AccidentService(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;
    }

    public Collection<Accident> findAll() {
        return accidentMem.findAll();
    }

    public Collection<AccidentType> findAllType() {
        return accidentMem.findAllType();
    }

    public Collection<Rule> findAllRule() {
        return accidentMem.findAllRule();
    }

    public void create(Accident accident) {
        accidentMem.create(accident);
    }

    public Accident findById(Integer id) {
       return accidentMem.findById(id);
    }

    public Rule findByIdRule(Integer id) {
        return accidentMem.findByIdRule(id);
    }

    public void update(Accident accident) {
        accidentMem.update(accident);
    }
}
