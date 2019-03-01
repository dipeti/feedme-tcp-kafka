package dinya.peter.feedme.messaging;

import dinya.peter.feedme.model.Domain;
import dinya.peter.feedme.model.Event;
import dinya.peter.feedme.model.Outcome;

import java.util.List;

public class DomainTestData {
    private static final List<String> EVENT_PROPS =
            List.of("msgId", "create", "event", "10", "eventId", "cat", "subcat", "name", "10", "1", "1");
    private static final List<String> OUTCOME_PROPS =
            List.of("msgId", "update", "outcome", "10", "marketId", "outcomeId", "name", "price", "1", "1");

    public static Domain anEventCreateMessage() {
        return new Event(EVENT_PROPS);
    }

    public static Domain anOutcomeUpdateMessage() {
        return new Outcome(OUTCOME_PROPS);
    }
}
