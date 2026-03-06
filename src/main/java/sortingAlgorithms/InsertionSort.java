package sortingAlgorithms;

public class InsertionSort implements SortingAlg{
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
    public int[] sort(int[] arr) {
        calculate.reset();
        int n = arr.length;
        for(int i = 1; i < n; i++){
            int key = arr[i];
            int j = i-1;
            while (j >= 0) {
                calculate.addComparisons();
                if(listener != null) {
                    listener.onCompare(arr, j, j + 1);
                }
                if(arr[j] > key) {
                    arr[j+1] = arr[j];
                    calculate.addSwaps();
                    if(listener != null) {
                        listener.onSwap(arr, j, j + 1);
                    }
                    j--;
                }
                else{break;}
            }
            if((j+1) != i) {
                arr[j+1] = key;
                calculate.addSwaps();
                if (listener != null) {
                    listener.onSwap(arr, j+1, i);
                }
            }
        }
        return arr;
    }
}
