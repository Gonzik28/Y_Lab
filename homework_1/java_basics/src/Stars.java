import java.util.Scanner;

public class Stars {
    public static void stars() {
        System.out.println("Enter two numbers and a padding character separated by spaces to build a shape: ");
        try (Scanner scanner = new Scanner(System.in)) {
            int one = scanner.nextInt();
            int two = scanner.nextInt();
            String template = scanner.next();

            scanner.close();
            for (int i = 0; i < one; i++) {
                for (int j = 0; j < two; j++) {
                    System.out.print(template + " ");
                }
                System.out.println();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
