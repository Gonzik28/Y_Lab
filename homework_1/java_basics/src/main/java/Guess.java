import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void guess() {
        int number = new Random().nextInt(100); // здесь загадывается число от 1 до 99
        int maxAttempts = 10; // здесь задается количество попыток
        System.out.println("Я загадал число от 1 до 99. У тебя " + maxAttempts + " попыток угадать.");
        int count = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            while (maxAttempts >= 0) {
                System.out.println("Введите число: ");
                int n = scanner.nextInt();
                count++;
                maxAttempts--;
                if (n == number) {
                    System.out.println("Ты угадал с " + count + " попытки");
                    break;
                }
                if (n > number) {
                    System.out.println("Мое число меньше! Осталось " + maxAttempts + " попыток");
                }
                if (n < number) {
                    System.out.println("Мое число больше! Осталось " + maxAttempts + " попыток");
                }
                if (maxAttempts == 0) {
                    System.out.println("Ты не угадал");
                    break;
                }
            }
        }
    }
}
