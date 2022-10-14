package fr.soat.mi.katasgcib.domain.usecase;

import fr.soat.mi.katasgcib.domain.exception.NotFoundAccountException;
import fr.soat.mi.katasgcib.domain.repository.AccountRepository;
import fr.soat.mi.katasgcib.domain.repository.HistoryRepository;
import fr.soat.mi.katasgcib.infra.logger.Logger;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.stream.Collectors;

public class History {
    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;

    public History(AccountRepository accountRepository, HistoryRepository historyRepository) {
        this.accountRepository = accountRepository;
        this.historyRepository = historyRepository;
    }

    public String execute(String accountName) throws IOException, NotFoundAccountException {
        if (!accountRepository.existsByName(accountName)) {
            throw new NotFoundAccountException(MessageFormat.format("account {0} not found", accountName));
        }
        return this.historyRepository.getAllAccounts()
                .stream()
                .filter(operation -> operation.accountName().equals(accountName))
                .map(operation -> MessageFormat.format("Operation : {0} - Date: {1} - Amount: {2} - Balance: {3}", operation.name(), operation.date(), operation.amount(), operation.balance()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
