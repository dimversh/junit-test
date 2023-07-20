import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String filename = "data.csv";

        List<Employee> list = parseCSV(columnMapping, filename);

        list.forEach(System.out::println);

        String json = listToJson(list);
        System.out.println(json);
        writeString(json);
    }


    public static List<Employee> parseCSV(String[] columnMap, String fileName) {
        List<Employee> stuff = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(fileName));) {
            //Стратегия маппинга
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMap);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            stuff = csv.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return stuff;
    }

   public static String listToJson(List<Employee> list) {

       GsonBuilder gsonBuilder = new GsonBuilder();
       Gson gson = gsonBuilder.create();
       Type listType = new TypeToken<List<Employee>>() {}.getType();

       String json = gson.toJson(list, listType);

       return json;
   }

   public static void writeString(String json) {
        try(FileWriter fw = new FileWriter("stuff.json");) {
            fw.write(json);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

   }

}
