package thymealeaf.demo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thymealeaf.demo.model.Employee;
import thymealeaf.demo.model.Project;
import thymealeaf.demo.model.WorkOnProject;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {
    private WorkOnProjectRepo workOnProjectRepo;
    private ProjectRepo projectRepo;
    public List<Project> getAllProjectsByEmployee(Employee employee) {
        List<WorkOnProject> wkp = workOnProjectRepo.findAllByEmployee(employee);

        List<Project> projects = new ArrayList<>();

        for (WorkOnProject w : wkp) {
            projects.add(projectRepo.getOne(w.getProject().getProject_id()));
        }

        return projects;
    }
}
