package src.Service;

import src.Evaluation.FalseNeg;
import src.Evaluation.FalsePoz;
import src.Evaluation.TrueNeg;
import src.Evaluation.TruePos;
import src.Instance.CSVReader;
import src.Instance.Instance;
import src.Model.KNN;
import src.Model.NaiveBayes;
import src.Model.Perceptron;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private String path;
    private KNN knn;
    private NaiveBayes naiveBayes;
    private Perceptron perceptron;

    public Service(String path) {
        if(path.isEmpty()) {
            throw new IllegalArgumentException("You have not selected a source file.");
        }
        this.path = path;
    }

    public void createKNN(int k){
            knn = new KNN<Double, Integer>(k);
    }

    public void createNaiveBayes(){
        naiveBayes = new NaiveBayes();
    }

    public void createPerceptron(double learningRate, int maxIterations){
        perceptron = new Perceptron(learningRate, maxIterations);
    }

    public void trainKNN(double split){
        try {
            CSVReader csvReader = new CSVReader();
            List<Instance<Double, Integer>> instances = csvReader.readCSV(path);
            int trainSize = (int)(instances.size() * (split/100));

            List<Instance<Double, Integer>> trainingData = instances.subList(0, trainSize);
            knn.train(trainingData);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void trainNaive(double split){
        try {
            CSVReader csvReader = new CSVReader();
            List<Instance<Double, Integer>> instances = csvReader.readCSV(path);
            int trainSize = (int)(instances.size() * (split/100));

            List<Instance<Double, Integer>> trainingData = instances.subList(0, trainSize);
            naiveBayes.train(trainingData);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void trainPerc(double split){
        try {
            CSVReader csvReader = new CSVReader();
            List<Instance<Double, Integer>> instances = csvReader.readCSV(path);
            int trainSize = (int)(instances.size() * (split/100));

            List<Instance<Double, Integer>> trainingData = instances.subList(0, trainSize);
            perceptron.train(trainingData);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Double> testKNN(double split){
        try {
            CSVReader csvReader = new CSVReader();
            List<Instance<Double, Integer>> instances = csvReader.readCSV(path);
            int trainSize = (int)(instances.size() * (split/100));

            List<Instance<Double, Integer>> testData = instances.subList(trainSize, instances.size());
            List<Integer> predictions = knn.test(testData);
            TruePos<Double, Integer> tp = new TruePos<>();
            TrueNeg<Double, Integer> tn = new TrueNeg<>();
            FalsePoz<Double, Integer> fp = new FalsePoz<>();
            FalseNeg<Double, Integer> fn = new FalseNeg<>();


            List<Double> results = new ArrayList<>();
            results.add(tp.evaluate(instances, predictions));
            results.add(tn.evaluate(instances, predictions));
            results.add(fp.evaluate(instances, predictions));
            results.add(fn.evaluate(instances, predictions));
            return results;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Double> testNaive(double split){
        try {
            CSVReader csvReader = new CSVReader();
            List<Instance<Double, Integer>> instances = csvReader.readCSV(path);
            int trainSize = (int)(instances.size() * (split/100));

            List<Instance<Double, Integer>> testData = instances.subList(trainSize, instances.size());
            List<Integer> predictions = naiveBayes.test(testData);
            TruePos<Double, Integer> tp = new TruePos<>();
            TrueNeg<Double, Integer> tn = new TrueNeg<>();
            FalsePoz<Double, Integer> fp = new FalsePoz<>();
            FalseNeg<Double, Integer> fn = new FalseNeg<>();


            List<Double> results = new ArrayList<>();
            results.add(tp.evaluate(instances, predictions));
            results.add(tn.evaluate(instances, predictions));
            results.add(fp.evaluate(instances, predictions));
            results.add(fn.evaluate(instances, predictions));
            return results;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Double> testPerc(double split){
        try {
            CSVReader csvReader = new CSVReader();
            List<Instance<Double, Integer>> instances = csvReader.readCSV(path);
            int trainSize = (int)(instances.size() * (split/100));

            List<Instance<Double, Integer>> testData = instances.subList(trainSize, instances.size());
            List<Integer> predictions = perceptron.test(testData);
            TruePos<Double, Integer> tp = new TruePos<>();
            TrueNeg<Double, Integer> tn = new TrueNeg<>();
            FalsePoz<Double, Integer> fp = new FalsePoz<>();
            FalseNeg<Double, Integer> fn = new FalseNeg<>();


            List<Double> results = new ArrayList<>();
            results.add(tp.evaluate(instances, predictions));
            results.add(tn.evaluate(instances, predictions));
            results.add(fp.evaluate(instances, predictions));
            results.add(fn.evaluate(instances, predictions));
            return results;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
