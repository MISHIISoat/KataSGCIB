package fr.soat.mi.katasgcib.domain.usecase;

import fr.soat.mi.katasgcib.domain.model.Account;
import fr.soat.mi.katasgcib.domain.repository.AccountRepository;
import fr.soat.mi.katasgcib.infra.logger.Logger;

import java.io.IOException;
import java.text.MessageFormat;

public class MakeADeposit {
    private final AccountRepository accountRepository;
    private final Logger logger;

    public MakeADeposit(AccountRepository accountRepository, Logger logger) {
        this.accountRepository = accountRepository;
        this.logger = logger;
    }

    public void deposit(String accountName, Double amount) throws IOException {
        var maybeAccount = accountRepository.findByName(accountName);
        var total = amount;
        if (maybeAccount.isEmpty()) {
            var newAccount = new Account(accountName, total);
            accountRepository.add(newAccount);
        } else {
            var foundAccount = maybeAccount.get();
            total += foundAccount.amount();
            var accountToUpdate = new Account(foundAccount.name(), total);
            accountRepository.update(accountToUpdate);
        }

        logger.out(MessageFormat.format("Save {0} in {1} account, total amount : {2}", amount, accountName, total));
    }
}
