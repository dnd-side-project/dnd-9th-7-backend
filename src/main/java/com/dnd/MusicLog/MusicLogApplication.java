package com.dnd.MusicLog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MusicLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicLogApplication.class, args);
	}

}
