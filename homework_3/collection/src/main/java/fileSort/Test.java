package fileSort;

import java.io.File;
import java.io.IOException;


public class Test {
    public static final int COUNT = 1_000;

    public static void main(String[] args) throws IOException {
        Sorter sorter = new Sorter();
        File dataFile = new Generator().generate("data.txt", COUNT);
        System.out.println(new Validator(dataFile).isSorted()); // false
        File sortedFile = sorter.sortFile(dataFile);
        System.out.println(new Validator(sortedFile).isSorted()); // true
        sorter.deleteFiles();
    }

}
