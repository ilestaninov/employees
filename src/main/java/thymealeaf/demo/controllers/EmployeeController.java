package thymealeaf.demo.controllers;

import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import thymealeaf.demo.model.Employee;
import thymealeaf.demo.repository.EmployeeRepository;

import javax.validation.Valid;
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
    public String showAddEmployeeForm(Model model){   ///*Employee employee*/
        model.addAttribute("employee",new Employee());
        return "add-employee";
    }
    @PostMapping("/add-employee")
    public String addEmployee(Model model,@Valid Employee employee, Errors errors){
        /*model.addAttribute("message","Successfully saved " + employee.toString())*/;
        if(errors.hasErrors()){
            System.out.println("HAS ERRORS");
            model.addAttribute("employee",employee);
            return "add-employee";
        }
        employeeRepository.save(employee);
        return "redirect:/employees/list";
    }
}
