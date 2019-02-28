package dinya.peter.feedme.model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Outcome extends Domain {
    private final String marketId;
    private final String outcomeId;
    private final String name;
    private final String price;
    private final Boolean displayed;
    private final Boolean suspended;

    public Outcome(List<String> properties) {
        super(properties);
        this.marketId = properties.get(4);
        this.outcomeId = properties.get(5);
        this.name = properties.get(6);
        this.price = properties.get(7);
        this.displayed = TRUE.equals(properties.get(8));
        this.suspended = TRUE.equals(properties.get(9));
    }
}
