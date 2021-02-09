package thymealeaf.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thymealeaf.demo.model.Absence;
import thymealeaf.demo.model.Employee;

import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence,Long> {
    List<Absence> findAllByEmployee(Employee employee);
}
