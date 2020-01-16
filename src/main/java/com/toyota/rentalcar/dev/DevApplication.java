package com.toyota.rentalcar.dev;

import com.toyota.rentalcar.dev.config.FileStorageProperties;
import com.toyota.rentalcar.dev.domain.TimeProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class DevApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevApplication.class, args);
	}

	@Bean
	public TimeProvider timeProvider() {
		return new TimeProvider();
	}
}
