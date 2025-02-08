package src.Model;
import src.Instance.Instance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Perceptron<F extends Number, L extends Integer> implements Model<F, L> {

    private List<Double> weights;
    private double bias;
    private double learningRate;
    private int maxIterations;

    public Perceptron(double learningRate, int maxIterations) {
        this.learningRate = learningRate;
        this.maxIterations = maxIterations;
    }

    private int predict(List<Double> features){
        double sum = 0.0;
        for (int i = 0; i < features.size(); i++){
            sum += features.get(i)*weights.get(i);
        }
        sum+=bias;
        return sum >= 0 ? 1: -1;
    }

    @Override
    public void train(List<Instance<F, L>> instances){
        int numFeatures = instances.get(0).getMyList().size();
        weights = new ArrayList<>(Collections.nCopies(numFeatures, 0.0)); //array of 0's
        bias =0.0;

        for (int i=0; i<maxIterations; i++) {
            boolean finished=true;
            for(Instance<F, L> instance : instances){
                List<Double> features = (List<Double>) instance.getMyList();
                int trueLabel= (double)instance.getLabel() == 1 ? 1: -1 ;

                int prediction = predict(features);
                if(prediction != trueLabel){
                    finished=false;
                    for (int j=0; j<features.size(); j++){
                        weights.set(j, weights.get(j)+learningRate*trueLabel*features.get(j));
                    }
                    bias+=learningRate*trueLabel;
                }
            }
            if(finished) break;
        }
    }

    @Override
    public List<L> test(List<Instance<F, L>> instances){
        List<L> predictions = new ArrayList<>();
        for (Instance<F, L> instance : instances) {
            int predictedLabel = predict( (List<Double>) instance.getMyList());
            predictions.add((L) (Integer) (predictedLabel == 1 ? 1 : 0));
        }
        return predictions;
    }
}
