package fr.soat.mi.katasgcib.domain.repository;

import fr.soat.mi.katasgcib.domain.model.Operation;

import java.io.IOException;
import java.util.List;

public interface HistoryRepository {

    List<Operation> getAllAccounts()throws IOException;

    void addOperation(Operation operation) throws IOException;
}
