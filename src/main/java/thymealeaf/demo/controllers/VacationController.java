package thymealeaf.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import thymealeaf.demo.enums.AbsenceType;
import thymealeaf.demo.enums.VacationStatus;
import thymealeaf.demo.helpers.Helpers;
import thymealeaf.demo.model.Absence;
import thymealeaf.demo.model.Employee;
import thymealeaf.demo.model.Vacation;
import thymealeaf.demo.repository.EmployeeRepository;
import thymealeaf.demo.repository.VacationRepository;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/vacations")
public class VacationController {
    private VacationRepository vacationRepository;
    private EmployeeRepository employeeRepository;
    private Helpers helpers;
    @GetMapping("/form")
    public String showForm(Vacation vacation, Model model){
        model.addAttribute("types", VacationStatus.values());
        model.addAttribute("vacation",vacation);
        return "add-vacation";
    }
    @GetMapping("list-vacations")
    public String listVacation(Model model, @Valid Vacation vacation){
        model.addAttribute("vacations", vacationRepository.findAll());
        return "list-vacations";
    }
    @PostMapping("add-vacation")
    public String addVacation(Model model, /*@Valid*/  Vacation vacation, BindingResult result) throws IOException {
        if(result.hasErrors()){
            System.out.println(result.getAllErrors());
            return "add-vacation";
        }
        Optional<Employee> employeeById= employeeRepository.findById(1L); //The user will be taken from spring security
        vacation.setEmployee(employeeById.get()); // Set the object to the many-to-one
        int differenceDays = helpers.getDifferenceDays(vacation.getVacation_start(), vacation.getVacation_end());
        vacation.setTotal_days(differenceDays);
        vacationRepository.save(vacation);
        return "redirect:/vacations/list-vacations";
    }
    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model){
        Optional<Vacation> vacationById = vacationRepository.findById(id);
        if(!vacationById.isPresent()){
            return "list-vacations";
        }
        model.addAttribute("types", VacationStatus.values());
        model.addAttribute("vacation",vacationById.get());
        return "update-vacation";
    }
    @PostMapping("update/{id}")
    public String updateVacation(@PathVariable("id") Long id, Vacation vacation, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            vacation.setVacation_id(id);
            return "update-vacation";
        }
        Optional<Employee> employeeById= employeeRepository.findById(1L); // The user will be taken from spring security
        vacation.setEmployee(employeeById.get()); // set the many-to-one relation
        vacation.setVacation_id(id); // Must set the same id in order to execute a PUT Request, otherwise will create
        int differenceDays = helpers.getDifferenceDays(vacation.getVacation_start(), vacation.getVacation_end());
        vacation.setTotal_days(differenceDays);
        vacationRepository.save(vacation);
        model.addAttribute("vacations",vacationRepository.findAll());
        return "redirect:/vacations/list-vacations";
    }
    @GetMapping("delete/{id}")
    public String deleteVacation(@PathVariable("id") Long id, Model model){
        Optional<Vacation> vacation = vacationRepository.findById(id);
        if(!vacation.isPresent()){
            return "list-vacations";
        }
        vacationRepository.delete(vacation.get());
        return "redirect:/vacations/list-vacations";
    }
}
