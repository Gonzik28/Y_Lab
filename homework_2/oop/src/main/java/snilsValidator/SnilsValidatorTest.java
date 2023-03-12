package snilsValidator;

public class SnilsValidatorTest {
    public static void main(String[] args) {
        String snilsValid = "90114404441";
        String snilsNotValid = "01468870570";

        System.out.println("EXPECTED TRUE - FACT " + new SnilsValidatorImpl().validate(snilsValid));
        System.out.println("EXPECTED FALSE - FACT " + new SnilsValidatorImpl().validate(snilsNotValid));
    }
}
