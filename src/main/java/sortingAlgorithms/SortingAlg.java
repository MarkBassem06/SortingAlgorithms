package sortingAlgorithms;

public interface SortingAlg {
    SortingCompCalculate getCalculation();
    void setListener(SortingListener listener);
    int[] sort(int[] arr);
}
