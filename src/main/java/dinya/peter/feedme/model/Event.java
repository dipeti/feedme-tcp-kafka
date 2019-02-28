package dinya.peter.feedme.model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Event extends Domain {
    private final String eventId;
    private final String category;
    private final String subCategory;
    private final String name;
    private final long startTime;
    private final boolean displayed;
    private final boolean suspended;

    public Event(List<String> properties) {
        super(properties);
        this.eventId = properties.get(4);
        this.category = properties.get(5);
        this.subCategory = properties.get(6);
        this.name = properties.get(7);
        this.startTime = Long.parseLong(properties.get(8));
        this.displayed = TRUE.equals(properties.get(9));
        this.suspended = TRUE.equals(properties.get(10));
    }
}
