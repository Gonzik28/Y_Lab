package statsAccumulator;

public class StatsAccumulatorImpl implements StatsAccumulator {
    private int value;
    private int count = 0;
    private int sum = 0;

    private double avg = 0.0;
    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE;

    @Override
    public void add(int value) {
        this.value = value;
        sum += value;
        if (count == 0) {
            min = value;
            max = value;
        } else if (value < min) {
            min = value;
        } else if (value > max) {
            max = value;
        }
        count++;
    }

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Double getAvg() {
        if (count != 0) {
            avg = Double.valueOf(sum) / Double.valueOf(count);
        }
        return avg;
    }
}
