package thymealeaf.demo.controllers;

import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import thymealeaf.demo.model.Employee;
import thymealeaf.demo.repository.EmployeeRepository;

import java.util.Optional;

@Controller
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    @GetMapping("/list")
    public String getSingleEmployee(Model model){
        model.addAttribute("employees", employeeRepository.findAll());
        return "index";
    }
    @GetMapping("/signup")
    public String showAddEmployeeForm(Employee employee, Model model){
        model.addAttribute("employee",employee);
        return "add-employee";
    }
    @PostMapping("/add-employee")
    public String addEmployee(Model model, Employee employee){
        employeeRepository.save(employee);
        return "redirect:/employees/list";
    }
}
