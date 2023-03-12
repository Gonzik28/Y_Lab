public class Main {
    public static void main(String[] args) {
//        MultTable.multTable();
        try {
//            Guess.guess();
//            Stars.stars();
            PellNumbers.pellNumbers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
