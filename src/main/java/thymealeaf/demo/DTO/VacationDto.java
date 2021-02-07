package thymealeaf.demo.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class VacationDto {
    private Long id;
    private Date start;
    private Date end;
    private String title;
}
