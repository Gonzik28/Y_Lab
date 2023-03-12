package snilsValidator;

public class SnilsValidatorImpl implements SnilsValidator {

    @Override
    public boolean validate(String snils) {
        char[] snilsNumber = snils.toCharArray();
        boolean result = false;
        int coefficient = 9;
        int controlSumm = 0;
        int controlNumber = 0;
        String twoNumber = "";
        for (int i = 0; i < snilsNumber.length; i++) {
            if (snilsNumber.length != 11) {
                return false;
            }
            if (Character.isDigit(snilsNumber[i])) {
                if (i < 9) {
                    controlSumm += Character.digit(snilsNumber[i], 10) * coefficient;
                    coefficient--;
                } else {
                    twoNumber += snilsNumber[i];
                }
            } else {
                return false;
            }
        }
        if (controlSumm < 100) {
            controlNumber = controlSumm;
        }
        if (controlSumm == 100) {
            controlNumber = 0;
        }
        if (controlSumm > 100) {
            int remainderOfDivision = controlSumm % 101;
            if (remainderOfDivision == 100) {
                controlNumber = 0;
            } else {
                controlNumber = remainderOfDivision;
            }
        }
        int a = Integer.parseInt(twoNumber);
        if (controlNumber == a) {
            result = true;
        }
        return result;
    }
}
