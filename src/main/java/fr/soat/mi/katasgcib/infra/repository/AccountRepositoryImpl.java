package fr.soat.mi.katasgcib.infra.repository;

import fr.soat.mi.katasgcib.domain.model.Account;
import fr.soat.mi.katasgcib.domain.repository.AccountRepository;
import fr.soat.mi.katasgcib.infra.parser.AccountParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class AccountRepositoryImpl implements AccountRepository {
    private final AccountParser accountParser;

    public AccountRepositoryImpl(AccountParser accountParser) {
        this.accountParser = accountParser;
    }

    @Override
    public Boolean existsByName(String accountName) throws IOException {
        return accountParser.getAllAccount()
                .stream()
                .anyMatch(account -> account.name().equals(accountName));
    }

    @Override
    public Optional<Account> findByName(String accountName) throws IOException {
        return accountParser.getAllAccount()
                .stream()
                .filter(account -> account.name().equals(accountName))
                .findFirst();
    }

    @Override
    public void add(Account account) throws IOException {
        var accountList = new ArrayList<>(accountParser.getAllAccount());
        accountList.add(account);

        accountParser.saveAllAccounts(accountList);
    }

    @Override
    public void update(Account accountToUpdate) throws IOException {
        var accounts = new ArrayList<>(accountParser.getAllAccount());

        var maybeAccount = accounts.stream()
                .filter(account -> accountToUpdate.name().equals(account.name()))
                .findFirst();
        maybeAccount.ifPresent(account -> {
            var accountIndex = accounts.indexOf(account);
            accounts.set(accountIndex, accountToUpdate);
        });

        accountParser.saveAllAccounts(accounts);
    }
}
