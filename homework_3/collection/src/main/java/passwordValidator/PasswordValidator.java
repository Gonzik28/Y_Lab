package passwordValidator;

import java.util.ArrayList;

public class PasswordValidator {

    public static Boolean passwordValidator(String login, String password, String confirmPassword) {
        Boolean result = false;
        try {
            if (loginValidate(login) && passwordValidate(password, confirmPassword)) {
                result = true;
            }
        } catch (WrongLoginException | WrongPasswordException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private static Boolean loginValidate(String login) throws WrongLoginException {
        char[] str = login.toCharArray();
        ArrayList<Boolean> list = new ArrayList<>();
        if (str.length >= 20) {
            list.add(false);
            throw new WrongLoginException("Login is too long");
        }
        for (char liter : str) {
            if (liter == '_') {
                list.add(true);
            } else if (liter >= '0' && liter <= '9') {
                list.add(true);
            } else if (liter >= 'A' && liter <= 'z') {
                list.add(true);
            } else {
                list.add(false);
                throw new WrongLoginException("Login contains unacceptable characters");
            }
        }
        return !list.contains(false);
    }

    private static Boolean passwordValidate(String password, String confirmPassword) throws WrongPasswordException {
        char[] str = password.toCharArray();
        ArrayList<Boolean> list = new ArrayList<>();
        if (!password.equals(confirmPassword)) {
            list.add(false);
            throw new WrongPasswordException("Password and confirm password doesn't match");
        }
        if (str.length >= 20) {
            list.add(false);
            throw new WrongPasswordException("Password is too long");
        }
        for (char liter : str) {
            if (liter == '_') {
                list.add(true);
            } else if (liter >= '0' && liter <= '9') {
                list.add(true);
            } else if (liter >= 'A' && liter <= 'z') {
                list.add(true);
            } else {
                list.add(false);
                throw new WrongPasswordException("Password contains unacceptable characters");
            }
        }
        return !list.contains(false);
    }

}
