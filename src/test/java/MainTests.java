import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

public class MainTests {


    List<Employee> expectedList;
    String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
    String filename = "data.csv";

    @BeforeEach
    public void beforeEach() {
        expectedList = new ArrayList<>();
        expectedList.add(new Employee(1, "John", "Smith", "USA", 25));
        expectedList.add(new Employee(2, "Ivan", "Petrov", "RU", 23));
    }

    @Test
    public void fileIsFoundTest() {
        Executable executable = () -> Main.parseCSV(columnMapping, filename);
        Assertions.assertDoesNotThrow(executable);
    }

    @Test
    public void parseCsvTest() {

        List<Employee> actualList = Main.parseCSV(columnMapping, filename);

        Assertions.assertEquals(expectedList, actualList);

    }


    @Test
    public void listToJsonTest() {

        List<Employee> empList = Main.parseCSV(columnMapping, filename);

        String expectedJson = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Smith\",\"country\":\"USA\",\"age\":25}," +
                "{\"id\":2,\"firstName\":\"Ivan\",\"lastName\":\"Petrov\",\"country\":\"RU\",\"age\":23}]";
        String actualJson = Main.listToJson(empList);


        boolean result = actualJson.equals(expectedJson);
        Assertions.assertEquals(expectedJson, actualJson);


    }

}
