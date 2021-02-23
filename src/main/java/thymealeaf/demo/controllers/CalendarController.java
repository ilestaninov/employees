package thymealeaf.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import thymealeaf.demo.DTO.*;
import thymealeaf.demo.enums.AbsenceType;
import thymealeaf.demo.helpers.Helpers;
import thymealeaf.demo.model.Absence;
import thymealeaf.demo.model.Employee;
import thymealeaf.demo.model.Project;
import thymealeaf.demo.model.Vacation;
import thymealeaf.demo.repository.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/calendar")
@AllArgsConstructor
public class CalendarController {

    private VacationRepository vacationRepository;
    private AbsenceRepository absenceRepository;
    private ProjectRepo projectRepo;
    private VacationConverter vacationConverter;
    private ProjectConverter projectConverter;
    private AbsenceConverter absenceConverter;
    private EmployeeRepository employeeRepository;
    private Helpers helpers;
    private ProjectService projectService;

    @GetMapping("newcalendar")
    public String newCalendar(Model model){
        return "new-calendar";
    }



    @GetMapping("")
    public String returnCalendar(Model model, Absence absence, Vacation vacation,Project project){
        model.addAttribute("absence", absence);
        model.addAttribute("types", AbsenceType.values());
        model.addAttribute("vacation",vacation);
        model.addAttribute("project", project);
        return "static-calendar";
    }
    @GetMapping("/list-vacations")
    @ResponseBody // is not working without responsebody (json)
    public String allVacationsForEmployee(){

        String jsonMsg = null;
        try {
            //List<Vacation> vacations = vacationRepository.findAll();
            Optional<Employee> employee =   employeeRepository.findById(1L);
            List<Vacation> vacations = vacationRepository.findAllByEmployee(employee.get());
            List<VacationDto> result = vacationConverter.entityToDto(vacations);
            ObjectMapper objectMapper = new ObjectMapper();
            jsonMsg = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            System.out.println("Enter catch");
            e.printStackTrace();
        }
        System.out.println(jsonMsg);
        return jsonMsg;
    }
    @GetMapping("/list-absences")
    @ResponseBody // is not working without responsebody (json)
    public String allAbsencesForEmployee(){

        String jsonMsg = null;
        try {
            Optional<Employee> employee =   employeeRepository.findById(1L);
            List<Absence> absences = absenceRepository.findAllByEmployee(employee.get());
            List<AbsenceDto> result = absenceConverter.entityToDto(absences);
            ObjectMapper objectMapper = new ObjectMapper();
            jsonMsg = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            System.out.println("Enter catch");
            e.printStackTrace();
        }
        System.out.println(jsonMsg);
        return jsonMsg;
    }
    @GetMapping("/list-projects")
    @ResponseBody // is not working without responsebody (json)
    public String allProjectsForEmployee(){

        String jsonMsg = null;
        try {
            Optional<Employee> employee =   employeeRepository.findById(1L); // employee should be returned from spring sec
            //List<Project> projects = projectRepo.findAllByWorkOnProjects(employee.get());
            List<Project> projects = projectService.getAllProjectsByEmployee(employee.get()); //employeeService.findByEmial(username)
            List<ProjectDto> result = projectConverter.entityToDto(projects);
            ObjectMapper objectMapper = new ObjectMapper();
            jsonMsg = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            System.out.println("Enter catch");
            e.printStackTrace();
        }
        System.out.println(jsonMsg  + " " + " All projects for employee");
        return jsonMsg;
    }
    @PostMapping("add-absence")
    public String addAbsence(Model model, @Valid Absence absence, BindingResult result) throws IOException {
        if(result.hasErrors()){
            System.out.println(result.getAllErrors());
            model.addAttribute("types", AbsenceType.values());
            model.addAttribute("absence",absence);
            model.addAttribute("message","Start  date cannot be less than end date");
            return "add-absence"; //redirect:/calendar
        }
        Optional<Employee> employeeById= employeeRepository.findById(1L); //The user will be taken from spring security
        absence.setEmployee(employeeById.get()); // Set the object to the many-to-one
        int differenceDays = helpers.getDifferenceDays(absence.getAbsence_start(), absence.getAbsence_end());
        absence.setTotal_days(differenceDays);
        absenceRepository.save(absence);
        return "redirect:/calendar";
    }
    @PostMapping("add-vacation")
    public String addVacation(Model model, @Valid Vacation vacation, BindingResult result) throws IOException {
        if(result.hasErrors()){
            System.out.println(result.getAllErrors());
            model.addAttribute("vacation",vacation);
            return "add-vacation";
        }
        Optional<Employee> employeeById= employeeRepository.findById(1L); //The user will be taken from spring security
        vacation.setEmployee(employeeById.get()); // Set the object to the many-to-one
        int differenceDays = helpers.getDifferenceDays(vacation.getVacation_start(), vacation.getVacation_end());
        vacation.setTotal_days(differenceDays);
        vacationRepository.save(vacation);
        return "redirect:/calendar";
    }
    @PostMapping("add-project")
    public String addProject(Model model, Project project, BindingResult result){
        if(result.hasErrors()){
            return "calendar";
        }
        projectRepo.save(project);
        return "redirect:/calendar";
    }
    @DeleteMapping("/absence/delete/{id}")
    public String deleteAbsence(@PathVariable("id") Long id, Model model){
        Optional<Absence> absenceToDelete = absenceRepository.findById(id);
        if(!absenceToDelete.isPresent()){
            return "list-absences";
        }
        absenceRepository.delete(absenceToDelete.get());
        return "redirect:/calendar";
    }
    @DeleteMapping("/vacation/delete/{id}")
    public String deleteVacation(@PathVariable("id") Long id, Model model){
        Optional<Vacation> vacation = vacationRepository.findById(id);
        if(!vacation.isPresent()){
            return "list-vacations";
        }
        vacationRepository.delete(vacation.get());
        return "redirect:/calendar";
    }
}
