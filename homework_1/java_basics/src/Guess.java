import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void guess() {
        int number = new Random().nextInt(100);
        int maxAttempts = 10;
        System.out.println("I came up with a number from 1 to 99. You have " + maxAttempts + " attempts to guess.");
        int count = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            while (maxAttempts >= 0) {
                System.out.println("Enter the number: ");
                int n = scanner.nextInt();
                count++;
                maxAttempts--;
                if (n == number) {
                    System.out.println("You guessed right on the " + count + " try");
                    break;
                }
                if (n > number) {
                    System.out.println("My number is less! " + maxAttempts + " attempts left");
                }
                if (n < number) {
                    System.out.println("My number is bigger! " + maxAttempts + " attempts left");
                }
                if (maxAttempts == 0) {
                    System.out.println("You did not guess right");
                    break;
                }
            }
        }
    }
}
