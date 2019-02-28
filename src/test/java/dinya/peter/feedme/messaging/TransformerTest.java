package dinya.peter.feedme.messaging;

import dinya.peter.feedme.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static dinya.peter.feedme.Resources.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransformerTest {
    private Transformer unit;

    @BeforeEach
    void setUp() {
        unit = new Transformer();
    }

    @Test
    void shouldReturnEvent() {
        unit.toDomain(EVENT_MESSAGE);
    }

    @Test
    void shouldReturnMarket() {
        unit.toDomain(MARKET_MESSAGE);
    }

    @Test
    void shouldReturnOutcome() {
        unit.toDomain(OUTCOME_MESSAGE);
    }

    @Test
    void shouldKeepEscapedPipes() {
        String expectedEventName = "|Watford| vs |Newcastle|";
        Event event = (Event) unit.toDomain(EVENT_MESSAGE);
        assertEquals(expectedEventName, event.getName());
    }

    @Test
    void shouldThrowIllegalArgumentException() {
        String unknown = "unknowm";
        String unknownMessage = OUTCOME_MESSAGE.replace("outcome", unknown);
        assertThrows(IllegalArgumentException.class, () -> unit.toDomain(unknownMessage));
    }
}