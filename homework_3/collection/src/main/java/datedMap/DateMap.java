package datedMap;

import java.time.LocalDateTime;

public class DateMap {
    String value;
    LocalDateTime timeAdd;

    public DateMap(String value, LocalDateTime timeAdd) {
        this.value = value;
        this.timeAdd = timeAdd;
    }

    public String getValue() {
        return value;
    }

    public LocalDateTime getTimeAdd() {
        return timeAdd;
    }
}
