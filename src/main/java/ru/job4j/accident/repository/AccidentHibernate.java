package ru.job4j.accident.repository;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.List;

@Repository
public class AccidentHibernate {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    public Accident create(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(accident);
            session.getTransaction().commit();
            session.close();
            return accident;
        }
    }

    public List<Accident> getAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery("select distinct a from Accident  a join fetch a.rules ", Accident.class)
                    .getResultList();
        }
    }

    public Collection<AccidentType> findAllType() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from  AccidentType", AccidentType.class)
                    .list();
        }
    }

    public Collection<Rule> findAllRule() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from  Rule", Rule.class)
                    .list();
        }
    }

    public Accident findById(Integer id) {
        try (Session session = sf.openSession()) {
            return session.get(Accident.class, id);
        }
    }

    public Rule findByIdRule(Integer id) {
        try (Session session = sf.openSession()) {
            return  session.get(Rule.class, id);
        }
    }

    public void update(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(accident);
            session.getTransaction().commit();
            session.close();
        }
    }

}
