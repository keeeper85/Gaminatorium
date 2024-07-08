package eu.gaminatorium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GaminatoriumMainServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GaminatoriumMainServiceApplication.class, args);
	}

}
