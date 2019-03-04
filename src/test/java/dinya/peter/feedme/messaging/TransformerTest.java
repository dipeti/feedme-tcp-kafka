package dinya.peter.feedme.messaging;

import dinya.peter.feedme.model.Event;
import dinya.peter.feedme.model.Market;
import dinya.peter.feedme.model.Outcome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static dinya.peter.feedme.Resources.*;
import static org.junit.jupiter.api.Assertions.*;

class TransformerTest {
    private Transformer unit;

    @BeforeEach
    void setUp() {
        unit = new Transformer();
    }

    @Test
    void shouldReturnEvent() {
        assertTrue(unit.toDomain(EVENT_MESSAGE) instanceof Event);
    }

    @Test
    void shouldReturnMarket() {
        assertTrue(unit.toDomain(MARKET_MESSAGE) instanceof Market);
    }

    @Test
    void shouldReturnOutcome() {
        assertTrue(unit.toDomain(OUTCOME_MESSAGE) instanceof Outcome);
    }

    @Test
    void shouldReturnOutcomeWithEventIdRef() {
        Market market = (Market) unit.toDomain(MARKET_MESSAGE);

        Outcome outcome = (Outcome) unit.toDomain(OUTCOME_MESSAGE);

        assertNotNull(outcome.getPartitionKey());
        assertEquals(outcome.getPartitionKey(), market.getEventId());
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