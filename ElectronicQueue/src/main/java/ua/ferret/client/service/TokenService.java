package ua.ferret.client.service;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import ua.ferret.client.model.Account;

@Service
public class TokenService {
	
	private @Autowired Key key;

	public Optional<String> createToken(Optional<Account> account) {
		return account.map(this::createToken);
	}
	
	public Optional<Claims> parseToken(HttpServletRequest request){
		return Optional.of("token").map(request::getParameter).map(this::createClaims);
	}
	
	private String createToken(Account account) {
		long issued = System.currentTimeMillis();
		long expiration = issued + TimeUnit.NANOSECONDS.convert(Duration.ofDays(31));
		return Jwts.builder()
				.signWith(key)
				.setIssuedAt(new Date(issued))
				.setExpiration(new Date(expiration))
				.claim("phone", account.getPhone())
				.claim("password", account.getPassword())
				.compact();
	}
	
	private Claims createClaims(String token) {
		return Jwts.parserBuilder()
					.setSigningKey(key).build()
					.parseClaimsJws(token)
					.getBody();
	}
	
}
