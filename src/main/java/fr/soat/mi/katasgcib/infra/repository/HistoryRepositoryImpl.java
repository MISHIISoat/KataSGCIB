package fr.soat.mi.katasgcib.infra.repository;

import fr.soat.mi.katasgcib.domain.model.Operation;
import fr.soat.mi.katasgcib.domain.repository.HistoryRepository;
import fr.soat.mi.katasgcib.infra.parser.HistoryParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepositoryImpl implements HistoryRepository {
    private final HistoryParser historyParser;

    public HistoryRepositoryImpl(HistoryParser historyParser) {
        this.historyParser = historyParser;
    }

    @Override
    public List<Operation> getAllOperation() throws IOException {
        return historyParser.getAllOperations();
    }

    @Override
    public void addOperation(Operation operation) throws IOException {
        var allOperations = new ArrayList<>(historyParser.getAllOperations());

        allOperations.add(operation);

        historyParser.saveAllOperations(allOperations);
    }
}
