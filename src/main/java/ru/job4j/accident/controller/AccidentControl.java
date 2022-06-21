package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.service.AccidentService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AccidentControl {
    private final AccidentService service;

    public AccidentControl(AccidentService service) {
        this.service = service;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("types", service.findAllType());
        model.addAttribute("rules", service.findAllRule());
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident,  HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> rules = new HashSet<>();
        if (ids != null) {
            for (String ruleId : ids) {
                rules.add(service.findByIdRule(Integer.parseInt(ruleId)));
            }
        }
        accident.setRules(rules);
        service.create(accident);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("types", service.findAllType());
        model.addAttribute("rules", service.findAllRule());
        model.addAttribute("accident", service.findById(id));
        return "accident/update";
    }
}
