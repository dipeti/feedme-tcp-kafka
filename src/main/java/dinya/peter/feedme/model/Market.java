package dinya.peter.feedme.model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Market extends Domain {
    private final String eventId;
    private final String marketId;
    private final String name;
    private final boolean displayed;
    private final boolean suspended;

    public Market(List<String> properties) {
        super(properties);
        this.eventId = properties.get(4);
        this.marketId = properties.get(5);
        this.name = properties.get(6);
        this.displayed = TRUE.equals(properties.get(7));
        this.suspended = TRUE.equals(properties.get(8));
    }

    @Override
    public String getPartitionKey() {
        return eventId;
    }
}
