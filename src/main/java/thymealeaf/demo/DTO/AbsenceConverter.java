package thymealeaf.demo.DTO;

import org.springframework.stereotype.Component;
import thymealeaf.demo.model.Absence;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AbsenceConverter {
    public AbsenceDto entityToDto(Absence absence){
        AbsenceDto dto = new AbsenceDto();
        dto.setId(absence.getAbsence_id());
        dto.setEnd(absence.getAbsence_end());
        dto.setStart(absence.getAbsence_start());
        dto.setTitle(absence.getAbsenceType().toString());

        return dto;
    }
    public List<AbsenceDto> entityToDto(List<Absence> absences){
        return absences.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
    }
}
