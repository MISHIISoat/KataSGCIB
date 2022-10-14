package fr.soat.mi.katasgcib.domain.repository;

import fr.soat.mi.katasgcib.domain.model.Account;

import java.io.IOException;
import java.util.Optional;

public interface AccountRepository {
    Boolean existsByName(String accountName) throws IOException;

    Optional<Account> findByName(String accountName) throws IOException;

    void add(Account account) throws IOException;

    void update(Account account) throws IOException;
}
