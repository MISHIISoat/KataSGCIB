package fr.soat.mi.katasgcib.infra.parser;

import fr.soat.mi.katasgcib.domain.model.Account;

import java.util.List;

public interface AccountParser {
    List<Account> getAllAccount();

    void saveAllAccounts(List<Account> accounts);
}
