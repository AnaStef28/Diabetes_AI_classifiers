package src.Model;

import src.Instance.Instance;

import java.util.*;
import java.util.stream.Collectors;



public class NaiveBayes<F extends Number, L> implements Model<F, L> {
    private Map<L, Double> priors;
    private Map<L, Map<Integer, Gaussian>> likelihoods;
    private Set<L> labels;

    private static class Gaussian{ //-----------------distribution
        double mean;
        double variance;
        Gaussian(double mean, double variance){
            this.mean = mean;
            this.variance = variance;
        }
    }

    private Map<L, Double> calculatePriors(List<Instance<F, L>> instances){

        Map<L, Long> labelCounts = instances.stream().collect(Collectors.groupingBy(Instance::getLabel, Collectors.counting()));

        double totalInstances = instances.size();
        Map<L, Double> priors = new HashMap<>();
        labels=labelCounts.keySet();

        for (Map.Entry<L, Long> entry : labelCounts.entrySet()){
            priors.put(entry.getKey(), entry.getValue()/totalInstances);
        }
        return priors;
    }

    private double calculateMean(List<Double> values){
        return values.stream().mapToDouble(v->v).average().orElse(0);
    }

    private double calculateVariance(List<Double> values, double mean){
        return values.stream().mapToDouble(v->Math.pow(v-mean, 2)).average().orElse(0);
    }

    private Map<L, Map<Integer, Gaussian>> calculateLikelihoods(List<Instance<F, L>> instances){
        Map<L, Map<Integer, List<Double>>> featureValuesByLabel = new HashMap<>();

        for(Instance<F, L> instance : instances){
            L label = instance.getLabel();
            featureValuesByLabel.putIfAbsent(label, new HashMap<>());
            List<Double> features = (List<Double>) instance.getMyList();
            for(int i=0; i<features.size(); i++){
                featureValuesByLabel.get(label).putIfAbsent(i, new ArrayList<>());
                featureValuesByLabel.get(label).get(i).add(features.get(i));
            }
        }

        Map<L, Map<Integer, Gaussian>> likelihoods = new HashMap<>();

        for (L label : featureValuesByLabel.keySet()){
            Map<Integer, Gaussian> featureLikelihoods = new HashMap<>();
            for (Map.Entry<Integer, List<Double>> entry : featureValuesByLabel.get(label).entrySet()){
                int featureIndex = entry.getKey();
                List<Double> values = entry.getValue();
                double mean=calculateMean(values);
                double variance=calculateVariance(values, mean);
                featureLikelihoods.put(featureIndex, new Gaussian(mean, variance));
            }
        likelihoods.put(label, featureLikelihoods);
        }
        return likelihoods;
    }

    private double calculateProbability(double x, Gaussian gaussian){
        double mean =gaussian.mean;
        double variance =gaussian.variance;
        if (variance==0)
            variance=1e-6;
        return (-0.5*Math.log(2*Math.PI*variance)-Math.pow(x-mean, 2)/(2*variance));
    }

    @Override
    public void train(List<Instance<F, L>> instances){
        priors = calculatePriors(instances);
        likelihoods = calculateLikelihoods(instances);
    }

    private L predict(List<F> features){
        Map<L, Double> posteriors = new HashMap<>();
        for(L label : labels){
            double posterior = Math.log(priors.get(label));
            for(int i=0; i<features.size(); i++){
                double feature = (double) features.get(i);
                Gaussian gaussian = likelihoods.get(label).get(i);
                posterior += calculateProbability(feature, gaussian);
            }
            posteriors.put(label, posterior);
        }
        return Collections.max(posteriors.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    @Override
    public List<L> test(List<Instance<F, L>> instances){
        List<L> predictions = new ArrayList<>();
        for(Instance<F, L> instance : instances){
            predictions.add(predict(instance.getMyList()));
        }
        return predictions;
    }
}
