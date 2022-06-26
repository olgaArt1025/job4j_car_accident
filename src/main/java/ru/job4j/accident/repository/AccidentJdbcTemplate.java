package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Accident create(Accident accident) {
        String insertSql = "insert into accident (name, text, address, accident_type_id) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into accident_rule (accident_id, rule_id) values (?, ?)",
                    keyHolder.getKey(),
                    rule.getId()
            );
        }
        return accident;
    }

    public List<Accident> findAll() {
        return jdbc.query("select a.id  id, a.name  name, a.text  text, a.address address,"
                       + " t.id  idType, t.name  nameType from accident a "
                       + "inner join accident_type t on  a.accident_type_id = t.id",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("idType"));
                    accidentType.setName(rs.getString("nameType"));
                    accident.setType(accidentType);
                    accident.setRules(Set.copyOf(findRuleByAccidentId(accident.getId())));
                    return accident;
                });
    }
    private List<Rule> findRuleByAccidentId(int id) {
        return jdbc.query("select * from accident_rule ar inner join rule r on r.id = ar.rule_id "
                        + " where accident_id = ?",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                },
                id);
    }


    public Collection<AccidentType> findAllType() {
        return jdbc.query("select * from accident_type",
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    return accidentType;
                });
    }
    public Collection<Rule> findAllRule() {
        return jdbc.query("select * from rule",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    public Accident findById(Integer id) {
        return jdbc.queryForObject("select a.id  id, a.name  name, a.text  text, a.address address,"
                        + " t.id  idType, t.name  nameType from accident a "
                        + " inner join accident_type t "
                        + "on a.accident_type_id = t.id  WHERE a.id = ?",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("idType"));
                    accidentType.setName(rs.getString("nameType"));
                    accident.setType(accidentType);
                    accident.setRules(Set.copyOf(findRuleByAccidentId(accident.getId())));
                    return accident;
                },
                id);
    }

    public Rule findByRuleId(int id) {
        return jdbc.queryForObject("select id, name from rule where id = ?",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                },
                id);
    }

    public Accident update(Accident accident) {
        jdbc.update("update accident set name = ?, text = ?, address = ?, accident_type_id = ? where id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId());
        jdbc.update("delete from accident_rule where accident_id = ?",
                accident.getId());
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into accident_rule (accident_id, rule_id) values (?, ?)",
                    accident.getId(),
                    rule.getId());
        }
        return accident;
    }
}
