package orgStructure;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrgStructureParserImpl implements OrgStructureParser {

    public static List<Employee> parserCSVSubordinate(File csvFile) {
        try {
            HashMap<Long, List<Employee>> careerLadder = new HashMap<>();
            List<Employee> employeeList = parserCSVWithoutSubordinate(csvFile);

            for (Employee employee : employeeList) {
                List<Employee> emp;
                if (careerLadder.containsKey(employee.getBossId())) {
                    emp = careerLadder.get(employee.getBossId());
                } else {
                    emp = new ArrayList<>();
                }
                emp.add(employee);
                careerLadder.put(employee.getBossId(), emp);
            }

            for (Employee employee : employeeList) {
                for (Employee bossEmployee : employeeList) {
                    if (bossEmployee.getId().equals(employee.getBossId())) {
                        employee.setBoss(bossEmployee);
                    }
                }
            }

            List<Employee> employees = new ArrayList<>();
            for (Employee employee : employeeList) {
                if (careerLadder.containsKey(employee.getId())) {
                    employee.setSubordinate(careerLadder.get(employee.getId()));
                    employees.add(employee);
                } else {
                    employees.add(employee);
                }
            }

            return employees;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Employee> parserCSVWithoutSubordinate(File csvFile) throws FileNotFoundException {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            ArrayList<String> lines = new ArrayList();
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                br.lines().forEach(x -> lines.add(x));
            }

            lines.remove(0);
            for (String line : lines) {
                String[] fragments = line.split(";");
                if (fragments.length != 4) {
                    throw new ParseException("incorrect value", fragments.length);
                }
                Employee employee = new Employee();
                employee.setId(Long.parseLong(fragments[0]));
                if (fragments[1].equals("")) {
                    employee.setBossId(null);
                } else {
                    employee.setBossId(Long.parseLong(fragments[1]));
                }
                employee.setName(fragments[2]);
                employee.setPosition(fragments[3]);
                employees.add(employee);
            }
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        List<Employee> employees = parserCSVSubordinate(csvFile);
        for (Employee employee : employees) {
            if (employee.getBossId() == null) {
                return employee;
            }
        }
        return null;
    }
}
