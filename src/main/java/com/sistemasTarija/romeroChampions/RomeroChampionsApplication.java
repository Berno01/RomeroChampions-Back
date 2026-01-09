package com.sistemasTarija.romeroChampions;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class RomeroChampionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RomeroChampionsApplication.class, args);
	}
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/La_Paz"));
    }
}
