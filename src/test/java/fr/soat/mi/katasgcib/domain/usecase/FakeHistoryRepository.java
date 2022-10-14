package fr.soat.mi.katasgcib.domain.usecase;

import fr.soat.mi.katasgcib.domain.model.Operation;
import fr.soat.mi.katasgcib.domain.repository.HistoryRepository;

import java.io.IOException;
import java.util.List;

public class FakeHistoryRepository implements HistoryRepository {
    @Override
    public List<Operation> getAllOperation() throws IOException {
        return null;
    }

    @Override
    public void addOperation(Operation operation) throws IOException {

    }
}
