package cybershield.data;

import cybershield.model.SecurityEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class CsvDataLoader {
    private static final String LOG_PATH = "dataset/security_logs.csv";
    private static final String NETWORK_PATH = "dataset/network.csv";
    private static final String CONFLICT_PATH = "dataset/security_conflicts.csv";

    private CsvDataLoader() { }

    public static List<SecurityEvent> loadEvents() throws IOException {
        List<SecurityEvent> events = new ArrayList<>();
        Path path = Paths.get(LOG_PATH);
        if (!Files.exists(path)) {
            throw new IOException("Dataset file not found: " + path.toAbsolutePath()
                    + "\nPlace the dataset folder in the project root.");
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line = reader.readLine(); // header
            if (line == null) throw new IOException("Dataset file is empty: " + path);

            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;
                String[] c = line.split(",", -1);
                if (c.length != 7) {
                    System.err.println("Skipping invalid CSV row " + lineNumber + ": expected 7 columns.");
                    continue;
                }
                for (int i = 0; i < c.length; i++) c[i] = c[i].trim();
                if (c[0].isEmpty() || c[1].isEmpty() || c[2].isEmpty()
                        || c[3].isEmpty() || c[4].isEmpty()
                        || c[5].isEmpty() || c[6].isEmpty()) {
                    System.err.println("Skipping invalid CSV row " + lineNumber + ": empty field.");
                    continue;
                }
                events.add(new SecurityEvent(c[0], c[1], c[2], c[3], c[4], c[5], c[6]));
            }
        }
        return events;
    }

    public static List<String[]> loadEdges(String filePath, int expectedColumns) throws IOException {
        List<String[]> rows = new ArrayList<>();
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new IOException("Dataset file not found: " + path.toAbsolutePath());
        }
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            if (line == null) throw new IOException("Dataset file is empty: " + path);
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;
                String[] c = line.split(",", -1);
                if (c.length != expectedColumns) {
                    System.err.println("Skipping invalid row " + lineNumber + " in " + filePath);
                    continue;
                }
                for (int i = 0; i < c.length; i++) c[i] = c[i].trim();
                rows.add(c);
            }
        }
        return rows;
    }
}
