package ua.ferret.client.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ua.ferret.client.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, UUID>{

	Optional<Account> findByPhone(String phone);
	
	boolean existsByPhone(String phone);

}
