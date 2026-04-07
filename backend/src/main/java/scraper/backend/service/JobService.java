package scraper.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scraper.backend.model.Job;
import scraper.backend.repository.JobRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public Page<Job> getAllJobs(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    public Optional<Job> storeJob(Job job) {
        if (jobRepository.existsByUrl(job.getUrl())) {
            return Optional.empty();
        }

        return Optional.of(jobRepository.save(job));
    }

    public Page<Job> searchJobs(String query, Pageable pageable) {
        return jobRepository.search(query, pageable);
    }
}
