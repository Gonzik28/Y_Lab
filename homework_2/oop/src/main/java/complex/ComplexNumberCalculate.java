package complex;

public interface ComplexNumberCalculate {
    double abs(ComplexNumber complexNumber);

    ComplexNumber sum(ComplexNumber numberOne, ComplexNumber numberTwo);

    ComplexNumber difference(ComplexNumber numberOne, ComplexNumber numberTwo);

    ComplexNumber multiplication(ComplexNumber numberOne, ComplexNumber numberTwo);

    ComplexNumber division(ComplexNumber numberOne, ComplexNumber numberTwo);
}
