package datedMap;

import java.time.LocalDateTime;
import java.util.Set;

public interface DatedMap {
    void put(String key, String value);

    String get(String key);

    boolean containsKey(String key);

    void remove(String key);

    Set<String> keySet();

    LocalDateTime getKeyLastInsertionDate(String key);
}
