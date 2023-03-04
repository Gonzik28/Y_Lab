public class Main {
    public static void main(String[] args) {
        MultTable.multTable();
        try {
//TODO запуск по одной программе за раз иначе она считает что ты уже ввел данные ранее и падает с RuntimeException

//            Guess.guess();
            Stars.stars();
//            PellNumbers.pellNumbers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
