package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;


public class AccidentHibernate {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    public Accident create(Accident accident) {
        return this.tx(
                session -> {
                    session.save(accident);
                    return accident;
                }
        );
    }

    public List<Accident> getAll() {
        return this.tx(
                session -> {
                    final List result = session.createQuery("select distinct a from Accident"
                                    + "  a join fetch a.rules ", Accident.class)
                            .getResultList();
                    return result;
                }
        );
    }

    public Collection<AccidentType> findAllType() {
        return this.tx(
                session -> {
                    final List result = session.createQuery("from  AccidentType", AccidentType.class).list();
                    return result;
                }
        );
    }

    public Collection<Rule> findAllRule() {
        return this.tx(
                session -> {
                    final List result = session.createQuery("from  Rule", Rule.class).list();
                    return result;
                }
        );
    }

    public Accident findById(Integer id) {
        return this.tx(
                session -> {
                    final Accident result = session.get(Accident.class, id);
                    return result;
                }
        );

    }

    public Rule findByIdRule(Integer id) {
        return this.tx(
                session -> {
                    final Rule result = session.get(Rule.class, id);
                    return result;
                }
        );
    }

    public Accident update(Accident accident) {
        return this.tx(
                session -> {
                    session.update(accident);
                    return accident;
                }
        );
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
