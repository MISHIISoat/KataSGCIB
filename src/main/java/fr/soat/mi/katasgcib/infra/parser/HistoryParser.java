package fr.soat.mi.katasgcib.infra.parser;

import fr.soat.mi.katasgcib.domain.model.Operation;

import java.io.IOException;
import java.util.List;

public interface HistoryParser {
    List<Operation> getAllOperations() throws IOException;

    void saveAllOperations(List<Operation> operations) throws IOException;
}
