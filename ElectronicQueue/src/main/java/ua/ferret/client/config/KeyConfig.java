package ua.ferret.client.config;

import java.security.Key;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class KeyConfig {

	@Bean
	public Key tokenKey(Environment environment) {
		String defaultSecurity = "agGAVGcrgvcfac8rg1rv8c93v24ar984fa1vc8c73A98cr2gca7gr24x98cf";
		String security = environment.getProperty("jwt.security.key", defaultSecurity);
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(security));
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
	
}
