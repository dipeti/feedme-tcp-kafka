package dinya.peter.feedme.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class Domain {
    protected static final String TRUE = "1";
    private final String msgId;
    private final String operation;
    private final String type;
    private final long timestamp;

    public Domain(List<String> properties) {
        var iterator = properties.iterator();
        this.msgId = iterator.next();
        this.operation = iterator.next();
        this.type = iterator.next();
        this.timestamp = Long.parseLong(iterator.next());
    }

    @JsonIgnore
    public abstract String getPartitionKey();
}
