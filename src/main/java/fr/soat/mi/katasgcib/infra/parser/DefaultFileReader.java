package fr.soat.mi.katasgcib.infra.parser;

import fr.soat.mi.katasgcib.infra.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultFileReader implements FileReader {
    private final Logger logger;

    public DefaultFileReader(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Boolean isFileExist(String filePath) {
        var file = new File(filePath);
        return file.exists();
    }

    @Override
    public String readTextFile(String filePath) throws IOException {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            var file = new File(filePath);
            if (file.createNewFile()) {
                logger.out("accounts file created");
            }
        }
        return "";
    }
}
