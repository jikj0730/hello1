package net.skhu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class}) //프로퍼티에 디비 입력 안해줬을 때
public class Hello1Application {

	public static void main(String[] args) {
		SpringApplication.run(Hello1Application.class, args);
	}

}

