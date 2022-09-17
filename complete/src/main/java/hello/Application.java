package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	@RequestMapping("/")
	public String home() {
		return "Hello World !! This web site is buit with Jenkins CICD and Azure web app";
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
