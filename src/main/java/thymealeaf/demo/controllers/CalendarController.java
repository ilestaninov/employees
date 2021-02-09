package thymealeaf.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import thymealeaf.demo.DTO.AbsenceConverter;
import thymealeaf.demo.DTO.AbsenceDto;
import thymealeaf.demo.DTO.VacationConverter;
import thymealeaf.demo.DTO.VacationDto;
import thymealeaf.demo.model.Absence;
import thymealeaf.demo.model.Employee;
import thymealeaf.demo.model.Vacation;
import thymealeaf.demo.repository.AbsenceRepository;
import thymealeaf.demo.repository.EmployeeRepository;
import thymealeaf.demo.repository.VacationRepository;

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

    @GetMapping("")
    public String returnCalendar(Model model){
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
}
