package fr.soat.mi.katasgcib.domain.usecase;

import fr.soat.mi.katasgcib.domain.model.Account;
import fr.soat.mi.katasgcib.domain.repository.AccountRepository;

public class MakeADeposit {
    private final AccountRepository accountRepository;

    public MakeADeposit(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void deposit(String accountName, Double amount) {
        var maybeAccount = accountRepository.findByName(accountName);
        if (maybeAccount.isEmpty()) {
            var newAccount = new Account(accountName, amount);
            accountRepository.add(newAccount);
        } else {
            var foundAccount = maybeAccount.get();
            var accountToUpdate = new Account(foundAccount.name(), foundAccount.amount() + amount);
            accountRepository.update(accountToUpdate);
        }

    }
}
