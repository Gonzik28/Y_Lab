import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void guess() {
        int number = new Random().nextInt(100); // ����� ������������ ����� �� 1 �� 99
        int maxAttempts = 10; // ����� �������� ���������� �������
        System.out.println("� ������� ����� �� 1 �� 99. � ���� " + maxAttempts + " ������� �������.");
        int count = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            while (maxAttempts >= 0) {
                System.out.println("������� �����: ");
                int n = scanner.nextInt();
                count++;
                maxAttempts--;
                if (n == number) {
                    System.out.println("�� ������ � " + count + " �������");
                    break;
                }
                if (n > number) {
                    System.out.println("��� ����� ������! �������� " + maxAttempts + " �������");
                }
                if (n < number) {
                    System.out.println("��� ����� ������! �������� " + maxAttempts + " �������");
                }
                if (maxAttempts == 0) {
                    System.out.println("�� �� ������");
                    break;
                }
            }
        }
    }
}
