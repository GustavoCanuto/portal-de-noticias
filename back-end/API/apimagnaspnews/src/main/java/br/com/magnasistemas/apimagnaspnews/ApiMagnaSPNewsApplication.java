package br.com.magnasistemas.apimagnaspnews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApiMagnaSPNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiMagnaSPNewsApplication.class, args);

	}

}
