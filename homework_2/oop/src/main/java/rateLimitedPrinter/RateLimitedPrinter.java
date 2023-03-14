package rateLimitedPrinter;

public class RateLimitedPrinter {
    private long interval;
    private long startTime;

    public RateLimitedPrinter(long interval) {
        this.interval = interval;
    }

    public void print(String message) {
        if (((System.nanoTime() - startTime) > interval)|(startTime == 0)){
            System.out.println(message);
            startTime = System.nanoTime();
        }
    }
}
