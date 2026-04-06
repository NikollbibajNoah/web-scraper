package scraper.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scraper.backend.model.Job;
import scraper.backend.service.JobService;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping("/jobs")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/jobs/search")
    public List<Job> searchJobs(String query) {
        return jobService.searchJobs(query);
    }
}
