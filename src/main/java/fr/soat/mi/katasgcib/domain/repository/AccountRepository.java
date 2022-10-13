package fr.soat.mi.katasgcib.domain.repository;

import fr.soat.mi.katasgcib.domain.model.Account;

import java.util.Optional;

public interface AccountRepository {
    Boolean existsByName(String accountName);

    Optional<Account> findByName(String accountName);

    void add(Account account);

    void update(Account account);
}
