package thymealeaf.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thymealeaf.demo.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
