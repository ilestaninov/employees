package thymealeaf.demo.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class AbsenceDto {
    private Long id;
    private Date start;
    private Date end;
    private String title;
}
