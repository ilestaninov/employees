package thymealeaf.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import thymealeaf.demo.enums.AbsenceType;
import thymealeaf.demo.enums.Status;
import thymealeaf.demo.enums.VacationStatus;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long absence_id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date absence_start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date absence_end;

    private int total_days;
    @Enumerated(EnumType.STRING)
    private AbsenceType absenceType;
    @Enumerated(EnumType.STRING)
    private VacationStatus status = VacationStatus.PENDING;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
