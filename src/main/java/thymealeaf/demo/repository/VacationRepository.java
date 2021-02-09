package thymealeaf.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import thymealeaf.demo.model.Employee;
import thymealeaf.demo.model.Vacation;

import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<Vacation,Long> {
    List<Vacation> findAllByEmployee(Employee employee);
}
