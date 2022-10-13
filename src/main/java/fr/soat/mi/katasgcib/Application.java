package fr.soat.mi.katasgcib;

import fr.soat.mi.katasgcib.domain.usecase.MakeADeposit;
import fr.soat.mi.katasgcib.infra.logger.ConsoleLogger;
import fr.soat.mi.katasgcib.infra.parser.AccountParserImpl;
import fr.soat.mi.katasgcib.infra.parser.DefaultFileReader;
import fr.soat.mi.katasgcib.infra.parser.DefaultFileWriter;
import fr.soat.mi.katasgcib.infra.repository.AccountRepositoryImpl;

import java.io.IOException;

public class Application {

    final public static String ACCOUNTS_FILE = "accounts.csv";

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Not enough arguments");
        }
        var action = args[0];
        if (action.equals("deposit") && args.length != 3) {
            throw new IllegalArgumentException("Deposit need 3 arguments");
        }
        var logger = new ConsoleLogger();
        var fileReader = new DefaultFileReader(logger);
        var fileWriter = new DefaultFileWriter();
        var accountParser = new AccountParserImpl(fileReader, fileWriter);
        var accountRepository = new AccountRepositoryImpl(accountParser);

        var makeADeposit = new MakeADeposit(accountRepository);

        var amount = args[1];
        var accountName = args[2];

        try {
            makeADeposit.deposit(accountName, Double.valueOf(amount));
        } catch (IOException e) {
            logger.err("Problem when make a deposit : " + e.getMessage());
        }
    }
}
