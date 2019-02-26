package dinya.peter.feedme.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TcpListenerTest {
    private static final String HOST = "localhost";
    private static final int PORT = 65530;
    private static final int BUFFER = 2;

    private TcpListener unit;

    @BeforeEach
    void setUp() throws Exception {
        unit = new TcpListener(HOST, PORT, BUFFER);
    }

    @Test
    public void shouldReadMessages() throws Exception {
        var messages = List.of("1", "2", "3");
        Thread serverThread = new Thread(() -> setUpMockServerAndSend(messages));
        Thread clientThread = new Thread(() -> unit.startListening());
        serverThread.start();
        clientThread.start();
        messages.forEach(message -> assertEquals(message, unit.takeMessage()));
    }

    private void setUpMockServerAndSend(List<String> messages) {
        try (ServerSocket mockServerSocket = new ServerSocket(PORT);
             Socket clientSocket = mockServerSocket.accept();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            messages.forEach(out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}