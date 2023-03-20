package datedMap;

public class DatedMapTest {
    public static void main(String[] args) {
        DatedMapImpl datedMap = new DatedMapImpl();
        datedMap.put("1", "One");
        datedMap.put("2", "Two");
        datedMap.put("3", "Three");
        datedMap.put("4", "Four");
        datedMap.put("5", "Five");
        System.out.println(datedMap.get("3"));
        System.out.println(datedMap.containsKey("4"));
        System.out.println(datedMap.getKeyLastInsertionDate("2"));
        System.out.println(datedMap.keySet());
        datedMap.remove("5");
        System.out.println(datedMap.getKeyLastInsertionDate("5"));
    }
}
