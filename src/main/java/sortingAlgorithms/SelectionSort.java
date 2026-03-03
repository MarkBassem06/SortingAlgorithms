package sortingAlgorithms;

public class SelectionSort implements SortingAlg{
    SortingCompCalculate calculate = new SortingCompCalculate();
    @Override
    public SortingCompCalculate getCalculation() {
        return calculate;
    }
    @Override
    public int[] sort(int[] arr){
        calculate.reset();
        int n = arr.length;
        for(int i = 0; i < n-1; i++){
            int idx = i;
            for(int j = i+1; j<n;j++){
                calculate.addComparisons();
                if(arr[j] < arr[idx]){
                    idx = j;
                }
            }
            if(idx != i) {
                int temp = arr[i];
                arr[i] = arr[idx];
                arr[idx] = temp;
                calculate.addSwaps();
            }
        }
        return arr;
    }
}
