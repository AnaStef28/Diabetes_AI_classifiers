package src.Model;

import src.Instance.Instance;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KNN<F extends Number, L> implements Model<F, L> {
    private List<Instance<F, L>> trainingData;
    private int k;

    public KNN(int k) {
        this.k = k; // nr of neighbours
    }

    private double distance(Instance<F, L> a, Instance<F, L> b) {
        List<F> featuresA = a.getMyList();
        List<F> featuresB = b.getMyList();
        double sum = 0;

        for (int i = 0; i < featuresA.size(); i++) {
            double diff= featuresA.get(i).doubleValue() - featuresB.get(i).doubleValue();
            sum += diff;
        }
        return Math.sqrt(sum);
    }

    private L getNeighboursLabels(List<Neighbour> neighbours) {
        //in a map we store the label and how many neighbours have this label
        Map<L, Long> count=neighbours.stream().collect(Collectors.groupingBy(Neighbour::getLabel, Collectors.counting()));
        //then we return the one with the highest count
        return Collections.max(count.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private L predict(Instance<F, L> unknown) {
        //we get a list of the first k neighbours (sorted by distance)
        List<Neighbour> neighbours = trainingData.stream().
                map(example->new Neighbour(example.getLabel(), distance(example, unknown))).
                sorted(Comparator.comparingDouble(Neighbour::getDistance)).
                limit(k).toList();
        //we return the most possible label
        return getNeighboursLabels(neighbours);
    }

    @Override
    public void train(List<Instance<F, L>> instances){
        this.trainingData = instances;

    }

    @Override
    public List<L> test(List<Instance<F, L>> instances){
        return instances.stream().map(this::predict).collect(Collectors.toList());
    }


    //Neighbour class
    private class Neighbour{
        private final L label;
        private final double distance;

        public Neighbour(L label, double distance){
            this.label = label;
            this.distance = distance;
        }
        public L getLabel() {
            return label;
        }
        public double getDistance() {
            return distance;
        }
    }

}


