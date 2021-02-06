package thymealeaf.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import thymealeaf.demo.enums.VacationStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vacation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private String comment;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}