package dinya.peter.feedme.messaging;

import dinya.peter.feedme.model.Domain;
import dinya.peter.feedme.service.TcpListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.messaging.support.MessageBuilder.createMessage;

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
            kafkaTemplate.send(createKafkaMessage(domain));
        }
        tcpListener.stopListening();
    }

    /**
     * Make sure 'create' messages are consumed in order i.e. end up on the same partition.
     *
     * @see org.apache.kafka.clients.producer.internals.DefaultPartitioner
     */
    private Message<?> createKafkaMessage(Domain domain) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(KafkaHeaders.MESSAGE_KEY, domain.getPartitionKey());
        headers.put(KafkaHeaders.TOPIC, topic);
        return createMessage(domain, new MessageHeaders(headers));
    }
}
