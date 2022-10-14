package fr.soat.mi.katasgcib.domain.usecase;

import fr.soat.mi.katasgcib.domain.repository.AccountRepository;
import fr.soat.mi.katasgcib.domain.repository.HistoryRepository;
import fr.soat.mi.katasgcib.infra.logger.Logger;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.stream.Collectors;

public class History {
    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;
    private final Logger logger;

    public History(AccountRepository accountRepository, HistoryRepository historyRepository, Logger logger) {
        this.accountRepository = accountRepository;
        this.historyRepository = historyRepository;
        this.logger = logger;
    }

    public String execute(String accountName) throws IOException {
        return this.historyRepository.getAllOperation().stream()
                .map(operation -> MessageFormat.format("Operation : {0}, Date: {1}, Amount: {2}, Balance: {3}", operation.name(), operation.date(), operation.amount(), operation.balance()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
