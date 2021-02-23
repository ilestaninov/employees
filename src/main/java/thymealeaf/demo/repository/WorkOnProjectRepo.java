package thymealeaf.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thymealeaf.demo.model.Employee;
import thymealeaf.demo.model.WorkOnProject;
import thymealeaf.demo.model.WorkOnProjectKey;

import java.util.List;

@Repository
public interface WorkOnProjectRepo extends JpaRepository<WorkOnProject,WorkOnProjectKey> {
    List<WorkOnProject> findAllByEmployee(Employee employee);
}
