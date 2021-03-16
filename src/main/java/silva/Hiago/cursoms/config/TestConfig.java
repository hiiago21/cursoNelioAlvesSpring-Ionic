package silva.Hiago.cursoms.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import silva.Hiago.cursoms.services.DBServices;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBServices dbServics;

	@Bean
	public boolean instantiateDataBase() throws ParseException {
		
		dbServics.instantiateTestDatabase();
		
		return true;
	}
}
