package com.homework.decerto;

import com.homework.decerto.entity.Quote;
import com.homework.decerto.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class DecertoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DecertoApplication.class, args);
	}


}
