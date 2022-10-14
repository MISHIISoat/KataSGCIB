package fr.soat.mi.katasgcib.domain.usecase;

import fr.soat.mi.katasgcib.domain.exception.ForbiddenAccountException;
import fr.soat.mi.katasgcib.domain.model.Account;
import fr.soat.mi.katasgcib.domain.model.Operation;
import fr.soat.mi.katasgcib.domain.repository.AccountRepository;
import fr.soat.mi.katasgcib.domain.repository.HistoryRepository;
import fr.soat.mi.katasgcib.infra.logger.Logger;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;

public class Deposit {
    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;
    private final Logger logger;

    public Deposit(AccountRepository accountRepository, HistoryRepository historyRepository, Logger logger) {
        this.accountRepository = accountRepository;
        this.historyRepository = historyRepository;
        this.logger = logger;
    }

    public void execute(String accountName, Double amount) throws IOException, ForbiddenAccountException {
        if (amount < 0) {
            throw new ForbiddenAccountException("The amount to deposit can't be negative");
        }
        Double total = saveAccountDeposit(accountName, amount);

        logger.out(MessageFormat.format("Save {0} in {1} account, total amount : {2}", amount, accountName, total));
    }

    private Double saveAccountDeposit(String accountName, Double amount) throws IOException {
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
        historyRepository.addOperation(new Operation("deposit", LocalDate.now(), amount, total));
        return total;
    }
}
