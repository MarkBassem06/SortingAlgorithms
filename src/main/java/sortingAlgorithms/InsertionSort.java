package sortingAlgorithms;

public class InsertionSort implements SortingAlg{
    SortingCompCalculate calculate = new SortingCompCalculate();
    @Override
    public SortingCompCalculate getCalculation() {
        return calculate;
    }
    @Override
    public int[] sort(int[] arr) {
        calculate.reset();
        int n = arr.length;
        for(int i = 1; i < n; i++){
            int key = arr[i];
            int j = i-1;
            while(j >= 0 && arr[j] > key){
                calculate.addComparisons();
                calculate.addSwaps();
                arr[j+1] = arr[j];
                j--;
            }
            if((j+1) != i) {
                arr[j+1] = key;
                calculate.addSwaps();
            }
        }
        return arr;
    }
}
