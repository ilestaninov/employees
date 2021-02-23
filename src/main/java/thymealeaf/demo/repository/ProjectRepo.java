package thymealeaf.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thymealeaf.demo.model.Employee;
import thymealeaf.demo.model.Project;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {
    /*List<Project> findAllBy(Employee employee);*/
}
