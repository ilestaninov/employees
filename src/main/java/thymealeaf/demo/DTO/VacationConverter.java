package thymealeaf.demo.DTO;

import org.springframework.stereotype.Component;
import thymealeaf.demo.model.Vacation;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VacationConverter {
    public VacationDto entityToDto(Vacation vacation){
        VacationDto dto = new VacationDto();
        dto.setId(vacation.getVacation_id());
        dto.setEnd(vacation.getVacation_end());
        dto.setStart(vacation.getVacation_start());
        dto.setTitle("Vacation"); //vacation.getComment()

        return dto;
    }
    public List<VacationDto> entityToDto(List<Vacation> vacations){
        return vacations.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
    }
    public Vacation dtoToEntity(VacationDto vacationDto){
        Vacation vacation = new Vacation();
        vacation.setVacation_id(vacationDto.getId());
        vacation.setVacation_start(vacationDto.getStart());
        vacation.setVacation_end(vacationDto.getEnd());
        vacation.setComment(vacationDto.getTitle());
        return vacation;
    }
    public List<Vacation> dtoToEntity(List<VacationDto> vacationsDto){
        return vacationsDto.stream().map(x->dtoToEntity(x)).collect(Collectors.toList());
    }

}
