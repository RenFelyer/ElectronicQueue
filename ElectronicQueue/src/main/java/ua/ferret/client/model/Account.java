package ua.ferret.client.model;

import static javax.persistence.EnumType.STRING;
import static ua.ferret.client.model.AccountRole.USER;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Account extends EntityID {

	@Column(nullable = false, unique = true)
	private String phone;

	@Column(nullable = false, unique = false)
	private String nickname = String.format("user#(%s)", RandomStringUtils.randomAlphabetic(5)) ;
	
	@JsonIgnore
	@Column(nullable = false, unique = false)
	private String password;
	
	@Enumerated(STRING)
	@Column(nullable = false)
	private AccountRole role = USER;

	public String getPhone() {return phone;}
	public String getNickname() {return nickname;}
	public String getPassword() {return password;}
	public AccountRole getRole() {return role;}

	public void setPhone(String phone) {this.phone = phone;}
	public void setRole(AccountRole role) {this.role = role;}
	public void setNickname(String nickname) {this.nickname = nickname;}
	public void setPassword(String password) {this.password = password;}
	
	@Override
	public int hashCode() {
		return Objects.hash(phone, password);
	}

}
