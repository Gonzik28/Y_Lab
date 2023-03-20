package passwordValidator;

public class PasswordValidatorTest {
    public static void main(String[] args) {
        String login = "Gonzik_28";
        String password = "1_Pass";
        String confirmPassword = "1_Pass";

        System.out.print("Valid: ");
        System.out.println(PasswordValidator.passwordValidator(login, password, confirmPassword));

        String loginNotValidSymbol = "Потапова";
        System.out.println(PasswordValidator.passwordValidator(loginNotValidSymbol, password, confirmPassword));

        String loginNotValidLong = "1234567dfghj_jhgfd234567";
        System.out.println(PasswordValidator.passwordValidator(loginNotValidLong, password, confirmPassword));


        String passwordNotEqualsConfirm = "2_Pass";
        System.out.println(PasswordValidator.passwordValidator(login, passwordNotEqualsConfirm, confirmPassword));

        String passwordNotValidLong = "2_Pastttttttttttttggggggggggjjjjjjjjj3455s";
        String confirmPasswordNotValidLong = "2_Pastttttttttttttggggggggggjjjjjjjjj3455s";
        System.out.println(PasswordValidator.passwordValidator(login, passwordNotValidLong, confirmPasswordNotValidLong));

        String passwordNotValidSymbol = "@root";
        String confirmPasswordNotValidSymbol = "@root";
        System.out.println(PasswordValidator.passwordValidator(login, passwordNotValidSymbol, confirmPasswordNotValidSymbol));


    }
}
