package at.technikum.wien.mse.swe.dslconnector.impl;

import at.technikum.wien.mse.swe.exception.SecurityAccountOverviewReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHelper {
    public static String readStringFromFile(Path file) {
        String content;
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            content = reader.readLine();
        } catch (IOException e) {
            throw new SecurityAccountOverviewReadException(e);
        }
        return content;
    }
}
