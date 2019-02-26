package dinya.peter.feedme.messaging;

import dinya.peter.feedme.service.TcpListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationStartedEvent;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageProcessorTest {
    @Mock
    private TcpListener mockTcpListener;
    @Mock
    private ApplicationStartedEvent mockEvent;
    private MessageProcessor unit;

    @BeforeEach
    void setUp() {
        unit = new MessageProcessor(mockTcpListener);
        when(mockTcpListener.isRunning()).thenReturn(true);
    }

    @Test
    void shouldCallStartListening() {
        unit.startMessageProcessing(mockEvent);
        verify(mockTcpListener).startListening();
    }

    @Test
    void shouldCallTakeMessage() {
        unit.startMessageProcessing(mockEvent);
        verify(mockTcpListener).takeMessage();
    }

    @Test
    void shouldCallStopListening() {
        unit.startMessageProcessing(mockEvent);
        verify(mockTcpListener).stopListening();
    }
}