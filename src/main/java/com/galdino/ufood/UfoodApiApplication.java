package com.galdino.ufood;

import com.galdino.ufood.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class UfoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UfoodApiApplication.class, args);
	}

}
