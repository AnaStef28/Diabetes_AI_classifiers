package src.Model;

import src.Instance.Instance;

import java.util.List;

public interface Model<F, L> {
    void train(List<Instance<F, L>> instances);
    List<L> test(List<Instance<F, L>> instances);
}
