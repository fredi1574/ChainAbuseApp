package ChainAbuse;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class LogManager {
    private static LogManager instance;
    private final String logFileName = "log.txt";
    private final List<String> logs;

    private LogManager() {
        logs = new ArrayList<>();
        loadLogsFromFile();
    }

    public static LogManager getInstance() {
        if (instance == null) {
            instance = new LogManager();
        }
        return instance;
    }

    private void loadLogsFromFile() {
        File file = new File(logFileName);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logs.add(line);
                }
            } catch (IOException e) {
                System.out.println("Error loading log file: " + e.getMessage());
            }
        }
    }

    public List<String> getLogs() {
        return logs;
    }

    public void addLog(String logEntry) {
        String logMessage = new Date() + " - " + logEntry;
        logs.add(logMessage);
        saveLogs();
    }

    public void saveLogs() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFileName, true))) {
            writer.println(logs.get(logs.size() - 1));
        } catch (IOException e) {
            System.out.println("Error saving log file: " + e.getMessage());
        }
    }
}
