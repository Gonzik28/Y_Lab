package datedMap;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;

public class DatedMapImpl implements DatedMap {
    private HashMap<String, DateMap> dateMap = new HashMap<>();

    @Override
    public void put(String key, String value) {
        dateMap.put(key, new DateMap(value, LocalDateTime.now()));
    }

    @Override
    public String get(String key) {
        DateMap date = dateMap.get(key);
        return date.getValue();
    }

    @Override
    public boolean containsKey(String key) {
        return dateMap.containsKey(key);
    }

    @Override
    public void remove(String key) {
        dateMap.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return dateMap.keySet();
    }

    @Override
    public LocalDateTime getKeyLastInsertionDate(String key) {
        if (dateMap.containsKey(key)) {
            return dateMap.get(key).getTimeAdd();
        }
        return null;
    }
}
