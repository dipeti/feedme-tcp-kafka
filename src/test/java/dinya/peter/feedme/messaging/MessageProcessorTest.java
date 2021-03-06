package dinya.peter.feedme.messaging;

import dinya.peter.feedme.model.Domain;
import dinya.peter.feedme.service.TcpListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageProcessorTest {
    private static final String TOPIC = "topic";
    @Mock
    private TcpListener mockTcpListener;
    @Mock
    private Transformer mockTransformer;
    @Mock
    private KafkaTemplate<?, Domain> mockKafkaTemplate;
    @Captor
    private ArgumentCaptor<Message<Domain>> argumentCaptor;
    private MessageProcessor unit;

    @BeforeEach
    void setUp() {
        unit = new MessageProcessor(mockTcpListener, mockTransformer, mockKafkaTemplate, TOPIC);
    }

    @Test
    void shouldCallStartListening() {
        unit.startMessageProcessing();
        verify(mockTcpListener).startListening();
    }

    @Test
    void shouldCallTakeMessage() {
        unit.startMessageProcessing();
        verify(mockTcpListener).takeMessage();
    }

    @Test
    void shouldCallStopListening() {
        unit.startMessageProcessing();
        verify(mockTcpListener).stopListening();
    }

    @Test
    void shouldCallKafkaTemplate() {
        Domain event = DomainTestData.anEventCreateMessage();
        when(mockTransformer.toDomain(anyString())).thenReturn(event);
        when(mockTcpListener.takeMessage()).thenReturn("someString").thenReturn(null);

        unit.startMessageProcessing();

        verify(mockKafkaTemplate).send(argumentCaptor.capture());
        Assertions.assertEquals(argumentCaptor.getValue().getPayload(), event);
    }

    @Test
    void shouldSetUpHeadersForCreateMessage() {
        Domain event = DomainTestData.anEventCreateMessage();
        when(mockTransformer.toDomain(anyString())).thenReturn(event);
        when(mockTcpListener.takeMessage()).thenReturn("someString").thenReturn(null);

        unit.startMessageProcessing();

        verify(mockKafkaTemplate).send(argumentCaptor.capture());

        Assertions.assertEquals(TOPIC, argumentCaptor.getValue().getHeaders().get(KafkaHeaders.TOPIC));
        Assertions.assertEquals(event.getPartitionKey(), argumentCaptor.getValue().getHeaders().get(KafkaHeaders.MESSAGE_KEY));
    }


    @Test
    void shouldCallTransformer() {
        String message = "asd";
        when(mockTcpListener.takeMessage()).thenReturn(message).thenReturn(null);
        when(mockTransformer.toDomain(anyString())).thenReturn(DomainTestData.anEventCreateMessage());
        unit.startMessageProcessing();

        verify(mockTransformer).toDomain(eq(message));
    }
}