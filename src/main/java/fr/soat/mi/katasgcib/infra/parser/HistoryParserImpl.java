package fr.soat.mi.katasgcib.infra.parser;

import fr.soat.mi.katasgcib.Application;
import fr.soat.mi.katasgcib.domain.model.Operation;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryParserImpl implements HistoryParser {

    private final FileReader fileReader;
    private final FileWriter fileWriter;

    public HistoryParserImpl(FileReader fileReader, FileWriter fileWriter) {
        this.fileReader = fileReader;
        this.fileWriter = fileWriter;
    }

    @Override
    public List<Operation> getAllOperations() throws IOException {
        var content = fileReader.readTextFile(Application.HISTORY_FILE);

        var operationsLines = new ArrayList<>(Arrays.stream(content.split(System.lineSeparator())).toList());
        if (operationsLines.size() == 0) return new ArrayList<>();
        operationsLines.remove(0);
        return operationsLines.stream()
                .map(this::mapToOperation)
                .toList();
    }

    private Operation mapToOperation(String line) {
        var splitLine = line.split(";");
        var name = splitLine[0];
        var date = splitLine[1];
        var amount = splitLine[2];
        var balance = splitLine[3];
        var accountName = splitLine[4];
        return new Operation(name, LocalDate.parse(date), Double.valueOf(amount), Double.valueOf(balance), accountName);
    }

    @Override
    public void saveAllOperations(List<Operation> operations) throws IOException {
        var content = getHeader() + getOperationLines(operations);

        fileWriter.writeContentToFile(content, Application.HISTORY_FILE);
    }

    private String getHeader() {
        return "operation;date;amount;balance;accountName" + System.lineSeparator();
    }


    private String getOperationLines(List<Operation> operations) {
        return operations.stream()
                .map(operation -> operation.name() + ";" +
                        operation.date().toString() + ";" +
                        operation.amount() + ";" +
                        operation.balance() + ";" +
                        operation.accountName() +
                        System.lineSeparator()).collect(Collectors.joining());
    }
}
