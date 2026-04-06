package scraper.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scraper.backend.model.Job;
import scraper.backend.repository.JobRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Optional<Job> storeJob(Job job) {
        if (jobRepository.existsByUrl(job.getUrl())) {
            return Optional.empty();
        }

        return Optional.of(jobRepository.save(job));
    }

    public List<Job> searchJobs(String query) {
        return jobRepository.search(query);
    }
}
