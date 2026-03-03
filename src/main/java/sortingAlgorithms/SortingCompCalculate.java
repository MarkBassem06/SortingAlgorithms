package sortingAlgorithms;

public class SortingCompCalculate {
    long comparisons;
    long swaps;

    void addComparisons() {
        comparisons++;
    }

    void addSwaps() {
        swaps++;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getSwaps() {
        return swaps;
    }

    void reset() {
        comparisons = 0;
        swaps = 0;
    }
}
