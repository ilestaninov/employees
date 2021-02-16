package thymealeaf.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import thymealeaf.demo.validation.ContactNumberConstraint;
import thymealeaf.demo.validation.FieldsValueMatch;

import javax.persistence.*;
import javax.validation.constraints.Min;
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
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "password_conf",
                message = "Passwords do not match!"
        )
})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long employee_id;
    //@ContactNumberConstraint
    @NotNull
    @Size(min=5, max=30,message = "First name must be between 5 and 30 chars")
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
