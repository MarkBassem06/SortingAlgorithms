package sortingAlgorithms;

public class BubbleSort implements SortingAlg{
    SortingCompCalculate calculate = new SortingCompCalculate();
    @Override
    public SortingCompCalculate getCalculation() {
        return calculate;
    }
    @Override
    public int[] sort(int[] arr){
        calculate.reset();
        int n = arr.length;
        for(int i = 0; i < n; i++){
            for(int j = 0; j< n-i-1;j++){
                calculate.addComparisons();
                if(arr[j+1] < arr[j]){
                    int temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                    calculate.addSwaps();
                }
            }
        }
        return arr;
    }
}
