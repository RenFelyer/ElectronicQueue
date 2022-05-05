package ua.ferret.client.model;

import static javax.persistence.EnumType.STRING;
import static ua.ferret.client.model.ProposalStatus.CONSIDERED;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Proposal extends EntityID {

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Account account;
	
	@Enumerated(STRING)
	@Column(nullable = false)
	private ProposalStatus status = CONSIDERED;

	public String getTitle() {return title;}
	public Account getAccount() {return account;}
	public ProposalStatus getStatus() {return status;}
	public String getDescription() {return description;}

	public void setTitle(String title) {this.title = title;}
	public void setAccount(Account account) {this.account = account;}
	public void setStatus(ProposalStatus status) {this.status = status;}
	public void setDescription(String description) {this.description = description;}

	@Override
	public int hashCode() {
		return Objects.hash(title, account);
	}
}
