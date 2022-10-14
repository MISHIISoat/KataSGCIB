package fr.soat.mi.katasgcib.infra.parser;

import java.io.IOException;

public interface FileReader {

    Boolean isFileExist(String filePath);

    String readTextFile(String filePath) throws IOException;
}
