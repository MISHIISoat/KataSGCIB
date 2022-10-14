package fr.soat.mi.katasgcib.domain.usecase;

import fr.soat.mi.katasgcib.domain.exception.ForbiddenAccountException;
import fr.soat.mi.katasgcib.domain.exception.NotFoundAccountException;
import fr.soat.mi.katasgcib.domain.model.Account;
import fr.soat.mi.katasgcib.domain.model.Operation;
import fr.soat.mi.katasgcib.domain.repository.AccountRepository;
import fr.soat.mi.katasgcib.domain.repository.HistoryRepository;
import fr.soat.mi.katasgcib.infra.logger.Logger;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;

public class Withdraw {
    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;
    private final Logger logger;

    public Withdraw(AccountRepository accountRepository, HistoryRepository historyRepository, Logger logger) {
        this.accountRepository = accountRepository;
        this.historyRepository = historyRepository;
        this.logger = logger;
    }

    public void execute(String accountName, Double amount) throws ForbiddenAccountException, IOException, NotFoundAccountException {
        if (amount < 0) {
            throw new ForbiddenAccountException("The amount to withdraw can't be negative");
        }
        var account = accountRepository.findByName(accountName).orElseThrow(() -> new NotFoundAccountException(MessageFormat.format("The account {0} not found", accountName)));
        if (account.amount() < amount) {
            throw new ForbiddenAccountException("The account has less than the amount to withdraw");
        }

        var updateAccount = new Account(account.name(), account.amount() - amount);
        accountRepository.update(updateAccount);

        historyRepository.addOperation(new Operation("withdraw", LocalDate.now(), amount, updateAccount.amount(), accountName));
    }
}
