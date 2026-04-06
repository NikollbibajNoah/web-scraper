package scraper.backend.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import scraper.backend.model.Job;
import scraper.backend.repository.JobRepository;
import scraper.backend.service.JobService;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobListener {

    private final JobService jobService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receive(Job job) {
        try {
            jobService.storeJob(job)
                .ifPresentOrElse(
                        saved -> log.info("Job gespeichert: {}", saved.getTitle()),
                        () -> log.info("Duplikat übersprungen: {}", job.getUrl())
                );
        } catch (Exception e) {
            log.error("Fehler beim Verarbeiten: {}", e.getMessage());
        }
    }
}