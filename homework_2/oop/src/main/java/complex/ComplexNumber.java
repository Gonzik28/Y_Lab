package complex;

public class ComplexNumber {
    private double realNumber;
    private double imaginaryNumber;

    public ComplexNumber(double realNumber) {
        this.realNumber = realNumber;
    }

    public ComplexNumber(double realNumber, double imaginaryNumber) {
        this.realNumber = realNumber;
        this.imaginaryNumber = imaginaryNumber;
    }

    public double getRealNumber() {
        return realNumber;
    }

    public void setRealNumber(double realNumber) {
        this.realNumber = realNumber;
    }

    public double getImaginaryNumber() {
        return imaginaryNumber;
    }

    public void setImaginaryNumber(double imaginaryNumber) {
        this.imaginaryNumber = imaginaryNumber;
    }

    @Override
    public String toString() {
        String result;
        if (imaginaryNumber == 0) {
            result = String.valueOf(realNumber);
        } else if (realNumber == 0) {
            result = imaginaryNumber + "i";
        } else if (imaginaryNumber < 0) {
            result = realNumber + "" + imaginaryNumber + "i";
        } else {
            result = realNumber + "+" + imaginaryNumber + "i";
        }
        return result;
    }
}
