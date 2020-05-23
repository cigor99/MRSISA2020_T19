package MRSISA.Klinicki.centar;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class KlinickiCentarApplication {

	public static void main(String[] args) {
		SpringApplication.run(KlinickiCentarApplication.class, args);
	}

}