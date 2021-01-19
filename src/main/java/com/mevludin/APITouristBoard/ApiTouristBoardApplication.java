package com.mevludin.APITouristBoard;

import com.mevludin.APITouristBoard.models.*;
import com.mevludin.APITouristBoard.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiTouristBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTouristBoardApplication.class, args);
	}

}
