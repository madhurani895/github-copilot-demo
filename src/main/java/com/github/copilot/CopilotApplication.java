package com.github.copilot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class CopilotApplication {

	public static void main(String[] args) {
		SpringApplication.run(CopilotApplication.class, args);
	}

}
