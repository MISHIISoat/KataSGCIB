package fr.soat.mi.katasgcib.infra.parser;

import fr.soat.mi.katasgcib.domain.model.Account;

import java.io.IOException;
import java.util.List;

public interface AccountParser {

    List<Account> getAllAccount() throws IOException;

    void saveAllAccounts(List<Account> accounts) throws IOException;
}
