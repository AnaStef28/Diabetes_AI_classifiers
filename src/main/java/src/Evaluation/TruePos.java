package src.Evaluation;

import src.Instance.Instance;

import java.util.List;

public class TruePos<F, L> implements EvaluationMeasure<F, L> {
    @Override
    public double evaluate(List<Instance<F, L>> instances, List<L> predictions) {
        int truePositive = 0;
        for (int i = 0; i<predictions.size(); i++) {
            if (predictions.get(i).equals(1)) {
                if (instances.get(instances.size()-predictions.size()+i).getLabel().equals(1)) {
                    truePositive++;
                }
            }
        }
        return (double) truePositive;
    }
}
