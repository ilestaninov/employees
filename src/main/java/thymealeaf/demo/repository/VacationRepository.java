package thymealeaf.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thymealeaf.demo.model.Vacation;

@Repository
public interface VacationRepository extends JpaRepository<Vacation,Long> {
}
