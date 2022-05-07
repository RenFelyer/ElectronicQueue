package ua.ferret.client.service;

import static ua.ferret.client.model.AccountRole.ADMIN;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.ferret.client.model.Account;
import ua.ferret.client.model.EntityID;
import ua.ferret.client.model.Proposal;
import ua.ferret.client.model.ProposalStatus;
import ua.ferret.client.repository.ProposalRepository;

@Service
public class ProposalService {
	
	private @Autowired ProposalRepository repository;

	public Optional<Proposal> createProposal(HttpServletRequest request, Optional<Account> account){
		Proposal proposal = new Proposal();
		Optional.of("title").map(request::getParameter).ifPresent(proposal::setTitle);
		Optional.of("description").map(request::getParameter).ifPresent(proposal::setDescription);
		account.ifPresent(proposal::setAccount);
		return Optional.of(proposal).filter(this::checkProposal).map(repository::save);
	}


	public Optional<Proposal> editProposal(HttpServletRequest request, Optional<Account> account) {
		return Optional.of("proposal").map(request::getParameter).map(UUID::fromString).map(repository::findById).orElseGet(Optional::empty).filter(proposal->{
			account.map(Account::getId).filter(proposal.getAccount().getId()::equals).ifPresent(value->{
				Optional.of("title").map(request::getParameter).ifPresent(proposal::setTitle);
				Optional.of("description").map(request::getParameter).ifPresent(proposal::setDescription);
			});
			
			account.map(Account::getRole).filter(ADMIN::equals).ifPresent(value->{
				Optional.of("status").map(request::getParameter).map(String::toUpperCase).map(ProposalStatus::valueOf).ifPresent(proposal::setStatus);
			});
			return true;
		}).filter(this::checkProposal).map(repository::save);
	}
	
	public List<Proposal> list(ProposalStatus... status) {
		return StreamSupport.stream(repository.findAll().spliterator(), true)
				.sorted(Comparator.comparingLong(value -> ((EntityID) value).getCreated().getTime()).reversed())
				.collect(Collectors.toList());
	}
	
	public List<Proposal> list(Optional<Account> account) {
		return account.map(repository::findByAccount).orElseGet(List::of);
	}
	
	private boolean checkProposal(Proposal proposal) {
		String description = proposal.getDescription();
		String title = proposal.getTitle();

		if(title == null || title.isBlank())
			throw new NoSuchElementException("Title cannot be empty");

		if(description == null || description.isBlank())
			throw new NoSuchElementException("Description cannot be empty");
		
		return true;
	}
}
