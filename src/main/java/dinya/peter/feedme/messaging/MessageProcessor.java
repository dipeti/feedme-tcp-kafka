package dinya.peter.feedme.messaging;

import dinya.peter.feedme.service.TcpListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MessageProcessor {
    private final TcpListener tcpListener;

    public MessageProcessor(TcpListener tcpListener) {
        this.tcpListener = tcpListener;
    }

    @EventListener
    public void startMessageProcessing(ApplicationStartedEvent event) {
        tcpListener.startListening();
        String message;
        while (tcpListener.isRunning() && (message = tcpListener.takeMessage()) != null) {
                transform(message);
        }
        tcpListener.stopListening();
    }

    private String transform(String message) {
        return null;
    }
}
