package complex;

public class ComplexNumberCalculateImp implements ComplexNumberCalculate {

    @Override
    public double abs(ComplexNumber complexNumber) {
        return Math.sqrt(Math.pow(complexNumber.getImaginaryNumber(), 2.0) +
                Math.pow(complexNumber.getRealNumber(), 2.0));
    }

    @Override
    public ComplexNumber sum(ComplexNumber... number) {
        double imaginaryNumberRes = 0.0;
        double realNumberRes = 0.0;
        for (int i = 0; i < number.length; i++) {
            imaginaryNumberRes += number[i].getImaginaryNumber();
            realNumberRes += number[i].getRealNumber();
        }
        return new ComplexNumber(realNumberRes, imaginaryNumberRes);
    }

    @Override
    public ComplexNumber difference(ComplexNumber... number) {
        double imaginaryNumberRes = number[0].getImaginaryNumber();
        double realNumberRes = number[0].getRealNumber();
        for (int i = 1; i < number.length; i++) {
            imaginaryNumberRes -= number[i].getImaginaryNumber();
            realNumberRes -= number[i].getRealNumber();

        }
        return new ComplexNumber(realNumberRes, imaginaryNumberRes);
    }

    @Override
    public ComplexNumber multiplication(ComplexNumber... number) {
        double realNumberRes = number[0].getRealNumber();
        double imaginaryNumberRes = number[0].getImaginaryNumber();
        for (int i = 1; i < number.length; i++) {
            double realNumberResTwo = number[i].getRealNumber();
            double imaginaryNumberResTwo = number[i].getImaginaryNumber();
            double realNumber = (realNumberRes * realNumberResTwo) - (imaginaryNumberRes * imaginaryNumberResTwo);
            double imaginaryNumber = (realNumberRes * imaginaryNumberResTwo) + (imaginaryNumberRes * realNumberResTwo);
            realNumberRes = realNumber;
            imaginaryNumberRes = imaginaryNumber;
        }
        return new ComplexNumber(realNumberRes, imaginaryNumberRes);
    }

    @Override
    public ComplexNumber division(ComplexNumber... number) {
        double realNumberRes = number[0].getRealNumber();
        double imaginaryNumberRes = number[0].getImaginaryNumber();
        for (int i = 1; i < number.length; i++) {
            double realNumberResTwo = number[i].getRealNumber();
            double imaginaryNumberResTwo = number[i].getImaginaryNumber();
            double imaginaryNumberResTwoDiff = -1.0 * imaginaryNumberResTwo;

            ComplexNumber numerator = multiplication(new ComplexNumber(realNumberRes, imaginaryNumberRes),
                    new ComplexNumber(realNumberResTwo, imaginaryNumberResTwoDiff));
            double denominator = Math.pow(realNumberResTwo, 2.0) + Math.pow(imaginaryNumberResTwoDiff, 2.0);
            if (denominator == 0) {
                System.out.println("Can't divide by 0");
            }
            realNumberRes = numerator.getRealNumber() / denominator;
            imaginaryNumberRes = numerator.getImaginaryNumber() / denominator;
        }
        return new ComplexNumber(realNumberRes, imaginaryNumberRes);
    }
}
