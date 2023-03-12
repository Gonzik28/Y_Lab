package sequences;

public class SequencesImpl implements Sequences {
    @Override
    public void a(int n) {
        if (n <= 0) {
            System.out.print("Enter a positive number or not equal to 0");
        }
        System.out.print("A: ");
        int sum = 0;
        for (int i = 0; i < n; i++) {
            System.out.print(sum += 2);
            if (i < (n - 1)) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }
    }

    @Override
    public void b(int n) {
        if (n <= 0) {
            System.out.print("Enter a positive number or not equal to 0");
        }
        System.out.print("B: ");
        int sum = -1;
        for (int i = 0; i < n; i++) {
            System.out.print(sum += 2);
            if (i < (n - 1)) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }
    }

    @Override
    public void c(int n) {
        if (n <= 0) {
            System.out.print("Enter a positive number or not equal to 0");
        }
        System.out.print("C: ");
        int inc = 1;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            System.out.print(sum += inc);
            inc += 2;
            if (i < (n - 1)) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }
    }

    @Override
    public void d(int n) {
        if (n <= 0) {
            System.out.print("Enter a positive number or not equal to 0");
        }
        System.out.print("D: ");
        for (int i = 1; i <= n; i++) {
            System.out.print((int) Math.pow(i, 3));
            if (i < (n)) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }
    }

    @Override
    public void e(int n) {
        if (n <= 0) {
            System.out.print("Enter a positive number or not equal to 0");
        }
        System.out.print("E: ");
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0) {
                System.out.print(-1);
            } else {
                System.out.print(1);
            }
            if (i < (n)) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }
    }

    @Override
    public void f(int n) {
        if (n <= 0) {
            System.out.print("Enter a positive number or not equal to 0");
        }
        System.out.print("F: ");
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0) {
                System.out.print(-i);
            } else {
                System.out.print(i);
            }
            if (i < (n)) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }
    }

    @Override
    public void g(int n) {
        if (n <= 0) {
            System.out.print("Enter a positive number or not equal to 0");
        }
        System.out.print("G: ");
        int inc = 1;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                System.out.print(sum += inc);
            } else {
                System.out.print((sum += inc) * -1);
            }
            inc += 2;
            if (i < (n - 1)) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }
    }

    @Override
    public void h(int n) {
        if (n <= 0) {
            System.out.print("Enter a positive number or not equal to 0");
        }
        System.out.print("H: ");
        int inc = 1;
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0) {
                System.out.print(0);
            } else {
                System.out.print(inc);
                inc++;
            }
            if (i < (n)) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }
    }

    @Override
    public void i(int n) {
        if (n <= 0) {
            System.out.print("Enter a positive number or not equal to 0");
        }
        System.out.print("I: ");
        int inc = 1;
        for (int i = 1; i <= n; i++) {
            inc = i * inc;
            System.out.print(inc);
            if (i < n) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }
    }

    @Override
    public void j(int n) {
        if (n <= 0) {
            System.out.print("Enter a positive number or not equal to 0");
        }
        System.out.print("J: ");
        int[] arr = new int[n];
        arr[0] = 1;
        arr[1] = 1;
        for (int i = 2; i < arr.length; ++i) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        for (int i = 0; i < arr.length; i++) {
            if (i < (arr.length - 1)) {
                System.out.print(arr[i] + ", ");
            } else {
                System.out.println(arr[i]);
            }
        }
    }
}
