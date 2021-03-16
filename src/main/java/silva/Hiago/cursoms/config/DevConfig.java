package silva.Hiago.cursoms.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import silva.Hiago.cursoms.services.DBServices;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBServices dbServics;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDataBase() throws ParseException {
		
		if(!"create".equals(strategy) ) {
			return false;
		}
		dbServics.instantiateTestDatabase();
		
		return true;
	}
}
