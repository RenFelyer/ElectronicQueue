package ua.ferret.client.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ua.ferret.client.model.Account;
import ua.ferret.client.model.Proposal;
import ua.ferret.client.model.ProposalStatus;

@Repository
public interface ProposalRepository extends CrudRepository<Proposal, UUID>{

	List<Proposal> findAllByStatus(ProposalStatus... status);
	List<Proposal> findByAccount(Account account);

}
