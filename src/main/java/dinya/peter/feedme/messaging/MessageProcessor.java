package dinya.peter.feedme.messaging;

import dinya.peter.feedme.model.Domain;
import dinya.peter.feedme.service.TcpListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MessageProcessor {
    private final TcpListener tcpListener;
    private final Transformer transformer;

    public MessageProcessor(TcpListener tcpListener, Transformer transformer) {
        this.tcpListener = tcpListener;
        this.transformer = transformer;
    }

    public void startMessageProcessing() {
        tcpListener.startListening();
        String message;
        while ((message = tcpListener.takeMessage()) != null) {
            Domain domain = transformer.toDomain(message);
            log.info(domain);
//            kafkaTemplate.send(domain);
        }
        tcpListener.stopListening();
    }
}
