package fr.soat.mi.katasgcib.domain.usecase;

import fr.soat.mi.katasgcib.domain.model.Operation;
import fr.soat.mi.katasgcib.domain.repository.HistoryRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FakeHistoryRepository implements HistoryRepository {
    private final List<Operation> operations;

    public FakeHistoryRepository() {
        this.operations = new ArrayList<>();
        this.operations.add(new Operation("withdraw", LocalDate.now(), 14.0, 20.2, "jane"));
        this.operations.add(new Operation("deposit", LocalDate.now(), 21.0, 30.0, "anotheruser"));
    }

    @Override
    public List<Operation> getAllAccounts() throws IOException {
        return this.operations;
    }

    @Override
    public void addOperation(Operation operation) throws IOException {
        this.operations.add(operation);
    }
}
