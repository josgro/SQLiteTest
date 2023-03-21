package com.example.SQLiteTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SqLiteTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqLiteTestApplication.class, args);


		DataService dataService = new DataService();
		UserInput userInput = new UserInput(dataService);
		userInput.run();

	}

}
