package gp.arttx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SecurityException.class)
public class ArttxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArttxApplication.class, args);
	}

}
