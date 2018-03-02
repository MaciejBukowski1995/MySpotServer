package com.myspot.myspot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class MyspotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyspotApplication.class, args);
	}

	@Bean
	CommandLineRunner init(AccountRepository accountRepository,
						   LogRepository bookmarkRepository) {
		return (evt) -> Arrays.asList(
				"jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
				.forEach(
						a -> {
							Account account = accountRepository.save(new Account(a,
									"password"));
							bookmarkRepository.save(new Log(account,
									"http://bookmark.com/1/" + a, "A description"));
							bookmarkRepository.save(new Log(account,
									"http://bookmark.com/2/" + a, "A description"));
						});
	}
}
