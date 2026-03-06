package sortingAlgorithms;

import java.util.Random;

public class QuickSort implements SortingAlg{
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
        return quick(arr, 0, arr.length-1);
    }
    public int[] quick(int[] arr, int st, int end){
        if(st < end){
            int keyIdx = partition(arr, st, end);
            quick(arr, st, keyIdx-1);
            quick(arr, keyIdx+1, end);
        }
        return arr;
    }
    public int partition(int[]arr, int st,int end){
        Random random = new Random();
        int idx = random.nextInt(st,end+1);
        int key = arr[idx];
        arr[idx] = arr[st];
        arr[st] = key;
        int j = st;
        for(int i = st+1; i <= end; i++){
            calculate.addComparisons();
            if (listener != null) {
                listener.onCompare(arr, i, j);
            }
            if(arr[i] <= key){
                j++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                calculate.addSwaps();
                if (listener != null) {
                    listener.onSwap(arr, i, j);
                }
            }
        }
        arr[st] = arr[j];
        arr[j] = key;
        calculate.addSwaps();
        if (listener != null) {
            listener.onSwap(arr, st, j);
        }
        return j;
    }
}
