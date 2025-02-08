package src.Evaluation;

import src.Instance.Instance;

import java.util.List;

public class FalsePoz<F, L> implements EvaluationMeasure<F, L> {
    @Override
    public double evaluate(List<Instance<F, L>> instances, List<L> predictions) {
        int falsePos = 0;
        for (int i = 0; i<predictions.size(); i++) {
            if (predictions.get(i).equals(1)){
                if (instances.get(instances.size()-predictions.size()+i).getLabel().equals(0)) {
                    falsePos++;
                }
            }
        }
        return (double) falsePos;
    }
}
