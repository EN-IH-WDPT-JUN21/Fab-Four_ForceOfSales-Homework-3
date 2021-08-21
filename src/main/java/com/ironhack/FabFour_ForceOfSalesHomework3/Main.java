package com.ironhack.FabFour_ForceOfSalesHomework3;

import com.ironhack.FabFour_ForceOfSalesHomework3.service.StartApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		StartApp.startApp();
	}

}
