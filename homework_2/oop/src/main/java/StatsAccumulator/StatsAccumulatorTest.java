package StatsAccumulator;

public class StatsAccumulatorTest {
    public static void main(String[] args) {
        StatsAccumulatorImpl statsAccumulator = new StatsAccumulatorImpl();
        System.out.println(statsAccumulator.getMin());
        System.out.println(statsAccumulator.getMax());
        System.out.println(statsAccumulator.getAvg());
        System.out.println(statsAccumulator.getCount());

        statsAccumulator.add(5);
        statsAccumulator.add(8);

        System.out.println(statsAccumulator.getCount());
        System.out.println(statsAccumulator.getAvg());

        statsAccumulator.add(-4);
        statsAccumulator.add(56);

        System.out.println(statsAccumulator.getMin());
        System.out.println(statsAccumulator.getMax());
    }
}
