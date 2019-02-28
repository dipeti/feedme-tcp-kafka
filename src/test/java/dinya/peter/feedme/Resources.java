package dinya.peter.feedme;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.readAllBytes;

public class Resources {
    public static String EVENT_MESSAGE;
    public static String MARKET_MESSAGE;
    public static String OUTCOME_MESSAGE;

    static {
        try {
            EVENT_MESSAGE = readFile("event-message.txt");
            MARKET_MESSAGE = readFile("market-message.txt");
            OUTCOME_MESSAGE = readFile("outcome-message.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFile(String s) throws IOException {
        return new String(readAllBytes(Path.of("src", "test", "resources", s)));
    }
}
