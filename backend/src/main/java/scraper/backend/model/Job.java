package scraper.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "jobs", uniqueConstraints = {
        @UniqueConstraint(columnNames = "url")
})
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    private String company;
    private String location;
    private String published;

    @Column(nullable = false, unique = true)
    private String url;
}
