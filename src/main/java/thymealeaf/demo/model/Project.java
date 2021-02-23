package thymealeaf.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import thymealeaf.demo.enums.ProjectStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@DynamicUpdate
@Table(name = "project")
@Getter @Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long project_id;

    private String name;
    private String p_description;
    private boolean active;

    @OneToMany(mappedBy = "project")
    private Set<WorkOnProject> workOnProjects;

//    @OneToMany(mappedBy = "project")
//    private Set<CategoryOnProject> categoryOnProjects;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startedOn;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date deadEnd;

    @Override
    public String toString() {
        return "Project{" +
                "Name='" + name + '\'' +
                ", Description='" + p_description + '\'' +
                '}';
    }
}