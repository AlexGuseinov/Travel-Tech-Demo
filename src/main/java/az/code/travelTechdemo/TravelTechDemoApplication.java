package az.code.travelTechdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TravelTechDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelTechDemoApplication.class, args);
	}

}
