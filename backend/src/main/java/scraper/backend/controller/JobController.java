package scraper.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scraper.backend.model.Job;
import scraper.backend.service.JobService;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping("/jobs")
    public Page<Job> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest request = PageRequest.of(page, size);

        return jobService.getAllJobs(request);
    }

    @GetMapping("/jobs/search")
    public Page<Job> searchJobs(
            String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest request = PageRequest.of(page, size);

        return jobService.searchJobs(query, request);
    }
}
