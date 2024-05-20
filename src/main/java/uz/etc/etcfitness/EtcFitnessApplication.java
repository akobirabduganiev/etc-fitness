package uz.etc.etcfitness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableScheduling
public class EtcFitnessApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtcFitnessApplication.class, args);
    }

}
