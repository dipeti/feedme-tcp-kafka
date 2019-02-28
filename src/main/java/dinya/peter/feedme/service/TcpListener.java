package dinya.peter.feedme.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Log4j2
public class TcpListener {
    private final AtomicBoolean running;
    private final String host;
    private final int port;
    private final BlockingQueue<String> buffer;

    public TcpListener(@Value("${tcp.host}") String host, @Value("${tcp.port}") int port, @Value("${tcp.buffer:1000}") int buffer) {
        this.host = host;
        this.port = port;
        this.buffer = new LinkedBlockingQueue<>(buffer);
        this.running = new AtomicBoolean(false);
    }

    @Async
    public void startListening() {
        if (running.get()) {
            log.warn("Listening is already ongoing. Should not start more than one listener.");
            return;
        }
        log.info("Data ingestion starting on thread [name={}]...", Thread.currentThread().getName());
        running.set(true);
        try (Socket socket = new Socket(host, port);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String line;
            while (running.get() && (line = bufferedReader.readLine()) != null) {
                buffer.put(line);
                log.info("An element was added to the buffer, current size=[{}]", buffer.size());
            }
        } catch (IOException e) {
            log.error("Stopped listening for updates on host=[{}], port=[{}] due to error:", host, port, e);
        } catch (InterruptedException e) {
            log.error("Failed to add element to buffer with size=[{}]", buffer.size(), e);
        }
        stopListening();
    }

    public void stopListening() {
        running.set(false);
        log.info("Listening stopped.");
    }

    public String takeMessage() {
        try {
            return buffer.take();
        } catch (InterruptedException e) {
            log.error("Failed to take element from buffer with size=[{}]", buffer.size(), e);
        }
        return null;
    }
}
