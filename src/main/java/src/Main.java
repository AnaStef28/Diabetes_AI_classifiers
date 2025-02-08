package src;

import src.Instance.CSVReader;
import src.Instance.Instance;
import src.Model.Perceptron;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // Read the data from the CSV file
        CSVReader csvReader = new CSVReader();
        List<Instance<Double, Integer>> instances = csvReader.readCSV("D:\\Facultate\\Java\\AssignmentGUI\\untitled\\Data\\diabetes.csv");

        // Split the data into training and test sets (80% training, 20% test)


//        int trainSize = (int) (instances.size() * 0.64);
//        List<Instance<Double, Integer>> trainingData = instances.subList(0, trainSize);
//        List<Instance<Double, Integer>> testData = instances.subList(trainSize, instances.size());
        int maxK=0;
            //15->64
//---------------------------------------------------------------------------Testing knn
//            KNN<Double, Integer> knnModel = new KNN<Double, Integer>(21);
//            knnModel.train(trainingData);
//            List<Integer> predictions = knnModel.test(testData);
//            double accuracy = calculateAccuracy(predictions, testData);
//            System.out.println("Accuracy: " + accuracy);

//---------------------------------------------------------------------------Testing Naive Bayes
//        int trainSize = (int) (instances.size() * 0.6);
//        List<Instance<Double, Integer>> trainingData = instances.subList(0, trainSize);
//        List<Instance<Double, Integer>> testData = instances.subList(trainSize, instances.size());
//        NaiveBayes<Double, Integer> naiveBayesModel = new NaiveBayes<Double, Integer>();
//
//        naiveBayesModel.train(trainingData);
//        List<Integer> predictions = naiveBayesModel.test(testData);
//
//        System.out.println("Predictions: " + predictions);
//        double accuracy = calculateAccuracy(predictions, testData);
//        System.out.println("Accuracy: " + accuracy);

//---------------------------------------------------------------------------Testing Perceptron
        int trainSize = (int) (instances.size() * 0.8);
        List<Instance<Double, Integer>> trainingData = instances.subList(0, trainSize);
        List<Instance<Double, Integer>> testData = instances.subList(trainSize, instances.size());
        Perceptron<Double, Integer> perceptron = new Perceptron<Double, Integer>(0.01, 10000);

        perceptron.train(trainingData);
        List<Integer> predictions = perceptron.test(testData);

        System.out.println("Predictions: " + predictions);
        double accuracy = calculateAccuracy(predictions, testData);
        System.out.println("Accuracy: " + accuracy);


 }



    public static double calculateAccuracy(List<Integer> predictedLabels, List<Instance<Double, Integer>> testData) {
        int correctPredictions = 0;
        for (int i = 0; i < testData.size(); i++) {
            if (predictedLabels.get(i).equals(testData.get(i).getLabel())) {
                correctPredictions++;
            }
        }
        return (double) correctPredictions / testData.size();
    }
}
