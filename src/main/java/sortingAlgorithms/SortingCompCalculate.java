package sortingAlgorithms;

public class SortingCompCalculate {
    int comparisons;
    int swaps;

    void addComparisons() {
        comparisons++;
    }

    void addSwaps() {
        swaps++;
    }

    public int getComparisons() {
        return comparisons;
    }

    public int getSwaps() {
        return swaps;
    }

    void reset() {
        comparisons = 0;
        swaps = 0;
    }
}
