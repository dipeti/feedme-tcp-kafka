package dinya.peter.feedme.messaging;

import dinya.peter.feedme.model.Domain;
import dinya.peter.feedme.model.Event;

import java.util.List;

public class DomainTestData {
    private static final List<String> EVENT_PROPS = List.of("string", "string", "string", "10", "string", "string", "string", "string", "10", "1", "1");

    public static Domain anEvent() {
        return new Event(EVENT_PROPS);
    }
}
