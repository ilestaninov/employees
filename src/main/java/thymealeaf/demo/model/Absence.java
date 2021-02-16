package thymealeaf.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import thymealeaf.demo.enums.AbsenceType;
import thymealeaf.demo.enums.VacationStatus;
import thymealeaf.demo.validation.DateRange;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@DateRange.List({
        @DateRange(absence_start = "absence_start",
                absence_end = "absence_end",
                message = "Start date should be earlier than end date.")
})
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
