package thymealeaf.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long employee_id;

    private String fname;
    private String lname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String email;
    private float salary;
    private String position;
    private String password;
    private String password_conf;

    @Override
    public String toString() {
        return String.format("Employee: %s %s", getFname(), getLname());
    }
}
