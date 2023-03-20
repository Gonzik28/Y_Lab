package orgStructure;

import java.io.File;
import java.io.IOException;

public class OrgStructureParserTest {
    public static void main(String[] args) {
        File file = new File("src/main/resources/orgStructure.csv");
        OrgStructureParserImpl orgStructureParser = new OrgStructureParserImpl();
        try {
            Employee employee = orgStructureParser.parseStructure(file);
            System.out.println(employee);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
