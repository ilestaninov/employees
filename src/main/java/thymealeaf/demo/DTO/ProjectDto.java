package thymealeaf.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProjectDto {
    private Long id;
    private String title;
    private Date start;
}
