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

	@Bean
	public CommandLineRunner mappingDemo(CountryRepository stateRepository, MunicipalityRepository municipalityRepository, SightRepository sightRepository, ReviewRepository reviewRepository){
		return args -> {
			try{
				Country country = new Country();
				country.setCountryName("Bosnia");
				country.setCountryAbbreviations("ba");
				stateRepository.save(country);


				Municipality municipality = new Municipality();
				municipality.setName("Zenica");
				municipality.setCountry(country);
				municipalityRepository.save(municipality);

				Sight sight = new Sight("Kuća Ive Andrića", "Bosansko-hercegovački Nobelovac", 11.304, 34.235, true, Importance.INEVITABLY, municipality);
				sightRepository.save(sight);

				Review review = new Review(5, sight);
				reviewRepository.save(review);
			} catch (Exception ex){
				System.out.println("Error : " + ex.getMessage());
			}

		};
	}
}
