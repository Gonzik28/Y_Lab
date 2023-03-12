package complex;

public class ComplexNumberTest {

    public static void main(String[] args) {

        ComplexNumber a = new ComplexNumber(4.2, -5.0);
        ComplexNumber b = new ComplexNumber(4.5);
        System.out.println(a);
        System.out.println(b);
        System.out.println("_______________________________________________________");

        ComplexNumberCalculateImp complexNumberCalculateImp = new ComplexNumberCalculateImp();

        ComplexNumber sum = complexNumberCalculateImp.sum(
                new ComplexNumber(2.0, 4.0),
                new ComplexNumber(1.0, -3.0));
        System.out.println(sum.toString());

        ComplexNumber difference = complexNumberCalculateImp.difference(
                new ComplexNumber(2.0, 4.0),
                new ComplexNumber(1.0, -3.0));
        System.out.println(difference.toString());

        ComplexNumber multiplication = complexNumberCalculateImp.multiplication(
                new ComplexNumber(2.0, 4.0),
                new ComplexNumber(1.0, -3.0));
        System.out.println(multiplication);

        ComplexNumber division = complexNumberCalculateImp.division(
                new ComplexNumber(5.0, -1.0),
                new ComplexNumber(2.0));
        System.out.println(division);

        double abs = complexNumberCalculateImp.abs(new ComplexNumber(4.0, 3.0));
        System.out.println(abs);
    }
}
