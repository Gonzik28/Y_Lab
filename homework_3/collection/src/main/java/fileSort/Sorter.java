package fileSort;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class Sorter {
    private static final int MAX_ELEMENT_FILE = 100;
    static ArrayList listName = new ArrayList();
    private static String fileName;
    private static int countFile = 1;

    public static File merge(File fileOne, File fileTwo) {
        String name = "merge_" + fileOne.getName();
        Path path = Paths.get(name);
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            FileReader frOne = new FileReader(fileOne);
            FileReader frTwo = new FileReader(fileTwo);
            BufferedReader readerOne = new BufferedReader(frOne);
            BufferedReader readerTwo = new BufferedReader(frTwo);
            String numberOne = readerOne.readLine();
            String numberTwo = readerTwo.readLine();
            while ((numberOne != null) | (numberTwo != null)) {
                if ((numberOne != null) & (numberTwo != null)) {
                    long a = Long.parseLong(numberOne);
                    long b = Long.parseLong(numberTwo);
                    if (a < b) {
                        bw.write(numberOne + '\n');
                        numberOne = readerOne.readLine();
                    } else {
                        bw.write(numberTwo + '\n');
                        numberTwo = readerTwo.readLine();
                    }
                } else if (numberOne == null) {
                    bw.write(numberTwo + '\n');
                    numberTwo = readerTwo.readLine();
                } else if (numberTwo == null) {
                    bw.write(numberOne + '\n');
                    numberOne = readerOne.readLine();
                }
            }
            readerOne.close();
            readerTwo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(String.valueOf(path));
    }

    public static void sortPathFile(File file) throws IOException {
        long count = readAndWriteNewFile(file);
        for (int j = 1; j <= count; ) {
            fileName = "example_" + (j++) + ".txt";
            ArrayList<Long> arrayList = new ArrayList<>();
            try {
                FileReader fr = new FileReader(fileName);
                listName.add(fileName);
                BufferedReader reader = new BufferedReader(fr);
                String line = reader.readLine();
                while (line != null) {
                    arrayList.add(Long.parseLong(line));
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Collections.sort(arrayList);

            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
                for (Long lines : arrayList) {
                    writer.write(lines.toString());
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static long readAndWriteNewFile(File file) {
        try {
            long size = 0;
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            FileWriter fileWriter = null;
            while (line != null) {
                if (size == (MAX_ELEMENT_FILE - 1)) {
                    size = 0;
                    fileWriter.append(line + '\n');
                    fileWriter.flush();
                    fileWriter.close();
                } else if (size == 0) {
                    size++;
                    fileName = "example_" + (countFile++) + ".txt";
                    fileWriter = new FileWriter(fileName, true);
                    fileWriter.append(line + '\n');
                    fileWriter.flush();
                } else if (size < MAX_ELEMENT_FILE) {
                    size++;
                    fileWriter.append(line + '\n');
                    fileWriter.flush();
                }
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return countFile - 1;
    }

    public static File sortFile(File dataFile) throws IOException {
        sortPathFile(dataFile);
        for (int i = 0; i < (listName.size() - 1); ++i) {
            if (i >= listName.size()) {
                listName.add(new File(listName.get(i).toString()));
            } else {
                listName.add(merge(new File(listName.get(i).toString()), new File(listName.get(++i).toString())));
            }
        }
        return new File(listName.get(listName.size() - 1).toString());
    }

    public static void deleteFiles() {
        for (int i = 0; i < listName.size() - 1; i++) {
            File file = new File(listName.get(i).toString());
            file.delete();
        }

    }
}


