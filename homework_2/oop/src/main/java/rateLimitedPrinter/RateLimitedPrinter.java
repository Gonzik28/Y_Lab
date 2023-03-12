package rateLimitedPrinter;

public class RateLimitedPrinter {
    private long interval;
    private long startTime;

    private long count = 1;

    public RateLimitedPrinter(long interval) {
        this.interval = interval;
    }

    public void print(String message) {
        if (count == 1) {
            System.out.println(message);
            startTime = System.nanoTime();
        } else if ((System.nanoTime() - startTime) > interval) {
            startTime = System.nanoTime();
            System.out.println(message);
        }
        count++;
    }
}
