package src.Instance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSVReader {

    public List<Instance<Double, Integer>> readCSV(String filePath) throws IOException {

        List<Instance<Double, Integer>> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                // Split the line by comma
                String[] parts = line.split(",");

                // Convert the first N-1 columns into features
                List<Double> features =
                        Arrays.stream(parts, 0, parts.length - 1)
                        .map(Double::parseDouble)
                        .collect(Collectors.toList());

                // The last column is the label
                Integer label = Integer.parseInt(parts[parts.length - 1]);

                // Create an Instance and add to the list
                data.add(new Instance<>(features, label));
            }
        }
        return data;
    }

//    public static void main(String[] args) {
//        try {
//            List<Instance<Double, Integer>> data = readCSV("D:\\Facultate\\Java\\AssignmentGUI\\untitled\\Data\\diabetes.csv");
//            data.forEach(instance -> System.out.println(instance.getMyList() + " -> " + instance.getLabel()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
