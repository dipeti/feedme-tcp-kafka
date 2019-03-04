package dinya.peter.feedme.messaging;

import dinya.peter.feedme.model.Domain;
import dinya.peter.feedme.model.Event;
import dinya.peter.feedme.model.Market;
import dinya.peter.feedme.model.Outcome;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Component
public class Transformer {
    // TODO: should really store this in Redis. Or even better: ancestors for selections
    private final Map<String, String> marketIdToEventIdMap = new HashMap<>();

    public Domain toDomain(String message) {
        List<String> properties = Arrays.stream(message.split("(?<!\\\\)\\|"))
                .skip(1)
                .map(s -> s.replace("\\", ""))
                .collect(toList());
        return toDomain(properties);
    }

    private Domain toDomain(List<String> properties) {
        String type = properties.get(2).toUpperCase();
        switch (Type.valueOf(type)) {
            case EVENT:
                return new Event(properties);
            case MARKET:
                Market market = new Market(properties);
                marketIdToEventIdMap.putIfAbsent(market.getMarketId(), market.getEventId());
                return market;
            case OUTCOME:
                return new Outcome(properties, marketIdToEventIdMap.get(properties.get(4)));
            default:
                throw new IllegalArgumentException(String.format("Failed to create instance of type=[%s]", type));
        }
    }

    enum Type {
        EVENT,
        MARKET,
        OUTCOME
    }
}
