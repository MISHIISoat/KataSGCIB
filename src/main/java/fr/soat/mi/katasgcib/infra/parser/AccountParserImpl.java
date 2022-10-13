package fr.soat.mi.katasgcib.infra.parser;

import fr.soat.mi.katasgcib.Application;
import fr.soat.mi.katasgcib.domain.model.Account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountParserImpl implements AccountParser {
    private final FileReader fileReader;
    private final FileWriter fileWriter;

    public AccountParserImpl(FileReader fileReader, FileWriter fileWriter) {
        this.fileReader = fileReader;
        this.fileWriter = fileWriter;
    }

    @Override
    public List<Account> getAllAccount() throws IOException {
        var content = fileReader.readTextFile(Application.ACCOUNTS_FILE);

        var accountLines = new ArrayList<>(Arrays.stream(content.split(System.lineSeparator())).toList());
        if (accountLines.size() == 0) return new ArrayList<>();
        accountLines.remove(0);
        return mapLinesToAccountsList(accountLines);
    }

    private List<Account> mapLinesToAccountsList(ArrayList<String> accountLines) {
        return accountLines.stream()
                .map(line -> {
                    var splitLine = line.split(";");
                    var name = splitLine[0];
                    var amount = splitLine[1];
                    return new Account(name, Double.valueOf(amount));
                }).toList();
    }

    @Override
    public void saveAllAccounts(List<Account> accounts) {

    }
}
