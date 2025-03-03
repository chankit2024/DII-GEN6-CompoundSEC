package manager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * LogManager => บันทึกทุก event + เวลา
 */
public class LogManager {
    private static LogManager instance = null;
    private List<String> logs;

    private LogManager() {
        logs = new ArrayList<>();
    }

    public static LogManager getInstance() {
        if (instance == null) {
            instance = new LogManager();
        }
        return instance;
    }

    public void addLog(String message) {
        // ใส่ Timestamp
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        logs.add("[" + timeStamp + "] " + message);
    }

    public void viewLogs() {
        if (logs.isEmpty()) {
            System.out.println("No logs available.");
            return;
        }
        System.out.println("===== LOGS =====");
        for (String log : logs) {
            System.out.println(log);
        }
    }
}
