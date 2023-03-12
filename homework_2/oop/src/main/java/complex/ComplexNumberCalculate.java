package complex;

public interface ComplexNumberCalculate {

    double abs(ComplexNumber complexNumber);

    ComplexNumber sum(ComplexNumber... number);

    ComplexNumber difference(ComplexNumber... number);

    ComplexNumber multiplication(ComplexNumber... number);

    ComplexNumber division(ComplexNumber... number);
}
