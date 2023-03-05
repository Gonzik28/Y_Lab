import java.util.Scanner;

public class PellNumbers {

    public static void pellNumbers() {
        System.out.println("Enter the ordinal number of the Pell number: ");
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            double pell = ((Math.pow((1 + Math.sqrt(2)), n) - Math.pow((1 - Math.sqrt(2)), n)) / (2 * Math.sqrt(2)));
            String thirdResult = String.format("%.0f", pell);
            System.out.println("Pell number with ordinal number " + n + " = " + thirdResult);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


}
