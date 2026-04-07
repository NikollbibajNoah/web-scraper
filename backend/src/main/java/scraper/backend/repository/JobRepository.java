package scraper.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import scraper.backend.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    boolean existsByUrl(String url);

    @Query("SELECT j FROM Job j WHERE " +
            ":query IS NULL OR " +
            "LOWER(j.title) LIKE LOWER(CONCAT('%', CAST(:query AS string), '%')) OR " +
            "LOWER(j.location) LIKE LOWER(CONCAT('%', CAST(:query AS string), '%')) OR " +
            "LOWER(j.company) LIKE LOWER(CONCAT('%', CAST(:query AS string), '%'))")
    Page<Job> search(@Param("query") String query, Pageable pageable);
}
