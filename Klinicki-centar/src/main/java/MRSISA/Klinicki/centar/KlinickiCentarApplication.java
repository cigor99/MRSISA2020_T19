package MRSISA.Klinicki.centar;

import java.util.Date;
import java.util.Timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableScheduling
public class KlinickiCentarApplication {

	public static void main(String[] args) {
		SpringApplication.run(KlinickiCentarApplication.class, args);

	}

}