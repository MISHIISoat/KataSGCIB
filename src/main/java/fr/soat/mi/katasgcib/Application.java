package fr.soat.mi.katasgcib;

import fr.soat.mi.katasgcib.domain.exception.ForbiddenAccountException;
import fr.soat.mi.katasgcib.domain.exception.NotFoundAccountException;
import fr.soat.mi.katasgcib.domain.usecase.Deposit;
import fr.soat.mi.katasgcib.domain.usecase.History;
import fr.soat.mi.katasgcib.domain.usecase.Withdraw;
import fr.soat.mi.katasgcib.infra.logger.ConsoleLogger;
import fr.soat.mi.katasgcib.infra.parser.AccountParserImpl;
import fr.soat.mi.katasgcib.infra.parser.DefaultFileReader;
import fr.soat.mi.katasgcib.infra.parser.DefaultFileWriter;
import fr.soat.mi.katasgcib.infra.parser.HistoryParserImpl;
import fr.soat.mi.katasgcib.infra.repository.AccountRepositoryImpl;
import fr.soat.mi.katasgcib.infra.repository.HistoryRepositoryImpl;

import java.io.IOException;
import java.text.MessageFormat;

public class Application {

    final public static String ACCOUNTS_FILE = "accounts.csv";
    public static final String HISTORY_FILE = "history.csv";

    public static void main(String[] args) {
        var logger = new ConsoleLogger();
        if (args.length < 2) {
            logger.err("Not enough arguments");
            return;
        }
        var action = args[0];
        if (action.equals("deposit") && args.length != 3) {
            logger.err("Deposit need 3 arguments");
            return;
        }

        var fileReader = new DefaultFileReader(logger);
        var fileWriter = new DefaultFileWriter();
        var accountParser = new AccountParserImpl(fileReader, fileWriter);
        var historyParser = new HistoryParserImpl(fileReader, fileWriter);

        var accountRepository = new AccountRepositoryImpl(accountParser);
        var historyRepository = new HistoryRepositoryImpl(historyParser);

        var makeADeposit = new Deposit(accountRepository, historyRepository, logger);
        var withDraw = new Withdraw(accountRepository, historyRepository, logger);
        var history = new History(accountRepository, historyRepository);

        switch (action) {
            case "deposit", "withdraw" -> depositOrWithdraw(args, logger, action, makeADeposit, withDraw);
            case "history" -> history(args, logger, history);
            default -> logger.err("Unknown action");
        }
    }

    private static void depositOrWithdraw(String[] args, ConsoleLogger logger, String action, Deposit makeADeposit, Withdraw withDraw) {
        try {
            var amount = Double.valueOf(args[1]);
            var accountName = args[2];
            if (action.equals("deposit")) {
                makeADeposit.execute(accountName, amount);

            } else {
                withDraw.execute(accountName, amount);
            }
        } catch (IOException | ForbiddenAccountException | NotFoundAccountException e) {
            logger.err(e.getMessage());
        }
    }

    private static void history(String[] args, ConsoleLogger logger, History history) {
        var accountName = args[1];
        try {
            var result = history.execute(accountName);
            logger.out(MessageFormat.format("History of {0} account :", accountName));
            logger.out(result);
        } catch (IOException | NotFoundAccountException e) {
            logger.err(e.getMessage());
        }
    }
}
