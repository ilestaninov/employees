package thymealeaf.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thymealeaf.demo.model.Absence;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence,Long> {
}
