package sortingAlgorithms;

public interface SortingListener {
    void onCompare(int[] arr, int i, int j);

    void onSwap(int[] arr, int i, int j);

}
