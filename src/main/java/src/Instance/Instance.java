package src.Instance;
import java.util.List;

public class Instance<F, L> {
    private List<F> myList;
    private L label;

    public Instance(List<F> list, L label) {
        this.myList = list;
        this.label = label;
    }

    public List<F> getMyList() {
        return myList;
    }
    public void setMyList(List<F> myList) {
        this.myList = myList;
    }
    public L getLabel() {
        return label;
    }
    public void setLabel(L label) {
        this.label = label;
    }

}
