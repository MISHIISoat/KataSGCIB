package fr.soat.mi.katasgcib.domain.usecase;

import fr.soat.mi.katasgcib.domain.model.Account;
import fr.soat.mi.katasgcib.domain.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeAccountRepository implements AccountRepository {
    private final List<Account> accounts;

    public FakeAccountRepository() {
        this.accounts = new ArrayList<>();
        this.accounts.add(new Account("jane", 100.0));
        this.accounts.add(new Account("doe", 7.5));
    }

    @Override
    public Boolean existsByName(String accountName) {
        return accounts.stream().anyMatch(account -> account.name().equals(accountName));
    }

    @Override
    public Optional<Account> findByName(String accountName) {
        return accounts.stream().filter(account -> account.name().equals(accountName)).findFirst();
    }

    @Override
    public void add(Account account) {
        accounts.add(account);
    }

    @Override
    public void update(Account account) {
        this.findByName(account.name()).ifPresent(foundAccount -> {
            var indexAccount = accounts.indexOf(foundAccount);
            accounts.set(indexAccount, account);
        });
    }


}
