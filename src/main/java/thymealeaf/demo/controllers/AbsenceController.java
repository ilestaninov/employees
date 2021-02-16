package thymealeaf.demo.controllers;

import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import thymealeaf.demo.enums.AbsenceType;
import thymealeaf.demo.enums.VacationStatus;
import thymealeaf.demo.helpers.Helpers;
import thymealeaf.demo.model.Absence;
import thymealeaf.demo.model.Employee;
import thymealeaf.demo.repository.AbsenceRepository;
import thymealeaf.demo.repository.EmployeeRepository;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/absences")
@AllArgsConstructor
public class AbsenceController {
    private AbsenceRepository absenceRepository;
    private EmployeeRepository employeeRepository;
    private Helpers helpers;
    @GetMapping("/form")
    public String showForm(Absence absence, Model model){
        //Optional<Employee> employeeById= employeeRepository.findById(1L);// this employee will come from the spring security
        model.addAttribute("types", AbsenceType.values());
        model.addAttribute("absence",absence);

        return "add-absence";
    }
    @GetMapping("list-absences")
    public String listAbsences(Model model,  Absence absence){ //@Valid

        model.addAttribute("absences", absenceRepository.findAll());
        return "list-absences";
    }
    @PostMapping("add-absence")
    public String addAbsence(Model model, @Valid Absence absence, BindingResult result) throws IOException {
        if(result.hasErrors()){
            System.out.println("ERRORS INSIDE ADD ABSENCE HAS ERRORS");
            System.out.println(result.getAllErrors());
            model.addAttribute("types", AbsenceType.values());
            model.addAttribute("absence",absence);
            return "add-absence";
        }
        Optional<Employee> employeeById= employeeRepository.findById(1L); //The user will be taken from spring security
        absence.setEmployee(employeeById.get()); // Set the object to the many-to-one
        int differenceDays = helpers.getDifferenceDays(absence.getAbsence_start(), absence.getAbsence_end());
        absence.setTotal_days(differenceDays);
        absenceRepository.save(absence);
        return "redirect:/absences/list-absences";
    }
    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model){
        Optional<Absence> absenceToDelete = absenceRepository.findById(id);
        if(!absenceToDelete.isPresent()){
            return "list-absences";
        }
        model.addAttribute("types", AbsenceType.values());
        model.addAttribute("absence",absenceToDelete.get());
        return "update-absence";
    }
    @PostMapping("update/{id}")
    public String updateAbsence(@PathVariable("id") Long id,@Valid Absence absence, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            absence.setAbsence_id(id);
            model.addAttribute("types", AbsenceType.values());
            model.addAttribute("absence",absence);
            return "update-absence";
        }
        Optional<Employee> employeeById= employeeRepository.findById(1L); // The user will be taken from spring security
        absence.setEmployee(employeeById.get()); // set the many-to-one relation
        absence.setAbsence_id(id); // Must set the same id in order to execute a PUT Request, otherwise will create
        int differenceDays = helpers.getDifferenceDays(absence.getAbsence_start(), absence.getAbsence_end());
        absence.setTotal_days(differenceDays);
        absence.setStatus(VacationStatus.PENDING); //when approved by admin if the user wants to make a update again again is set to pending.
        absenceRepository.save(absence);
        model.addAttribute("absences",absenceRepository.findAll());
        return "redirect:/absences/list-absences";
    }
    @GetMapping("delete/{id}")
    public String deleteAbsence(@PathVariable("id") Long id, Model model){
        Optional<Absence> absenceToDelete = absenceRepository.findById(id);
        if(!absenceToDelete.isPresent()){
            return "list-absences";
        }
        absenceRepository.delete(absenceToDelete.get());
        return "redirect:/absences/list-absences";
    }

}
