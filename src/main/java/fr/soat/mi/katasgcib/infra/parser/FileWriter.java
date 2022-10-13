package fr.soat.mi.katasgcib.infra.parser;

import java.io.IOException;

public interface FileWriter {
    void writeContentToFile(String content, String filePath) throws IOException;
}
