public class MultTable {
    public static void multTable() {
        System.out.println("Распечатаю таблицу умножения: ");
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                System.out.println(i + " * " + j + " = " + i * j);
            }
        }
    }
}
