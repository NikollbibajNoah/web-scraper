package scraper.backend.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import scraper.backend.model.Job;
import scraper.backend.repository.JobRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobListener {

    private final JobRepository jobRepository;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receive(Job job) {
        try {
            jobRepository.save(job);
            log.info("Job gespeichert: {}", job.getTitle());
        } catch (Exception e) {
            log.error("Fehler beim Verarbeiten der Nachricht: {}", e.getMessage());
        }
    }
}