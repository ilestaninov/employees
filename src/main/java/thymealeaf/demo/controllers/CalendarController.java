package thymealeaf.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import thymealeaf.demo.DTO.AbsenceConverter;
import thymealeaf.demo.DTO.AbsenceDto;
import thymealeaf.demo.DTO.VacationConverter;
import thymealeaf.demo.DTO.VacationDto;
import thymealeaf.demo.enums.AbsenceType;
import thymealeaf.demo.helpers.Helpers;
import thymealeaf.demo.model.Absence;
import thymealeaf.demo.model.Employee;
import thymealeaf.demo.model.Vacation;
import thymealeaf.demo.repository.AbsenceRepository;
import thymealeaf.demo.repository.EmployeeRepository;
import thymealeaf.demo.repository.VacationRepository;

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
    private VacationConverter vacationConverter;
    private AbsenceConverter absenceConverter;
    private EmployeeRepository employeeRepository;
    private Helpers helpers;

    @GetMapping("")
    public String returnCalendar(Model model, Absence absence, Vacation vacation){
        model.addAttribute("absence", absence);
        model.addAttribute("types", AbsenceType.values());
        model.addAttribute("vacation",vacation);
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
    @PostMapping("add-absence")
    public String addAbsence(Model model, @Valid Absence absence, BindingResult result) throws IOException {
        if(result.hasErrors()){
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
}
