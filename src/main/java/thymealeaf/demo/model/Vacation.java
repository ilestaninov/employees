package thymealeaf.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import thymealeaf.demo.enums.VacationStatus;
import thymealeaf.demo.enums.VacationType;
import thymealeaf.demo.validation.DateRange;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vacation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DateRange.List({
        @DateRange(absence_start = "vacation_start",
                absence_end = "vacation_end",
                message = "Start date should be earlier than end date.")
})
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vacation_id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vacation_start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vacation_end;
    private int total_days;

    @Enumerated(EnumType.STRING)
    private VacationStatus status = VacationStatus.PENDING;
    /*@Enumerated(EnumType.STRING)
    private VacationType type;*/
    private String comment;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}