package sortingAlgorithms;

public class SelectionSort implements SortingAlg{
    SortingCompCalculate calculate = new SortingCompCalculate();
    SortingListener listener;
    @Override
    public SortingCompCalculate getCalculation() {
        return calculate;
    }

    @Override
    public void setListener(SortingListener listener){
        this.listener = listener;
    }
    @Override
    public int[] sort(int[] arr){
        calculate.reset();
        int n = arr.length;
        for(int i = 0; i < n-1; i++){
            int idx = i;
            for(int j = i+1; j<n;j++){
                calculate.addComparisons();
                if (listener != null) {
                    listener.onCompare(arr, j, idx);
                }
                if(arr[j] < arr[idx]){
                    idx = j;
                }
            }
            if(idx != i) {
                int temp = arr[i];
                arr[i] = arr[idx];
                arr[idx] = temp;
                calculate.addSwaps();
                if (listener != null) {
                    listener.onSwap(arr, i, idx);
                }
            }
        }
        return arr;
    }
}
