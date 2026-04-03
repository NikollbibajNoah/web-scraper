package scraper.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scraper.backend.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}
