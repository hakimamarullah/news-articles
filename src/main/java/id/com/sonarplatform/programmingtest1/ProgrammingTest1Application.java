package id.com.sonarplatform.programmingtest1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProgrammingTest1Application {

	public static void main(String[] args) {
		SpringApplication.run(ProgrammingTest1Application.class, args);
	}

}
