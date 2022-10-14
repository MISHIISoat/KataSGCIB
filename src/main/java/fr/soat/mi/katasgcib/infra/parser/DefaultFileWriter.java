package fr.soat.mi.katasgcib.infra.parser;

import java.io.BufferedWriter;
import java.io.IOException;

public class DefaultFileWriter implements FileWriter {
    @Override
    public void writeContentToFile(String content, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(filePath));
        writer.write(content);
        writer.close();
    }
}
