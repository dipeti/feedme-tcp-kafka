package dinya.peter.feedme.messaging;

import dinya.peter.feedme.model.Domain;
import dinya.peter.feedme.service.TcpListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MessageProcessor {
    private final TcpListener tcpListener;
    private final Transformer transformer;
    private final KafkaTemplate<?, Domain> kafkaTemplate;
    private final String topic;

    public MessageProcessor(TcpListener tcpListener, Transformer transformer,
                            KafkaTemplate<?, Domain> kafkaTemplate, @Value("${spring.kafka.topic}") String topic) {
        this.tcpListener = tcpListener;
        this.transformer = transformer;
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void startMessageProcessing() {
        tcpListener.startListening();
        String message;
        while ((message = tcpListener.takeMessage()) != null) {
            Domain domain = transformer.toDomain(message);
            kafkaTemplate.send(topic, domain);
        }
        tcpListener.stopListening();
    }
}
