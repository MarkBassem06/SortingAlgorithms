package sortingAlgorithms;

public class HeapSort implements SortingAlg{
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
        arr = build_max_heap(arr);
        int end = arr.length -1;
        while(end>0){
            int temp = arr[end];
            arr[end] = arr[0];
            arr[0] = temp;
            end--;
            calculate.addSwaps();
            if (listener != null) {
                listener.onSwap(arr, 0, end+1);
            }
            max_heapify(arr,0, end);
        }
        return arr;
    }
    public void max_heapify(int[] arr, int idx, int end){
        int l = idx*2 +1;
        int r = idx*2 +2;
        int largest = idx;
        calculate.addComparisons();
        if (listener != null) {
            listener.onCompare(arr, l, largest);
        }
        if(l <= end && arr[l] > arr[largest]){
            largest = l;
        }
        calculate.addComparisons();
        if (listener != null) {
            listener.onCompare(arr, r, largest);
        }
        if(r <= end && arr[r] > arr[largest]){
            largest = r;
        }
        if(largest != idx){
            int temp = arr[idx];
            arr[idx] = arr[largest];
            arr[largest] = temp;
            calculate.addSwaps();
            if (listener != null) {
                listener.onSwap(arr, idx, largest);
            }
            max_heapify(arr, largest, end);
        }
    }
    public int[] build_max_heap(int[] arr){
        for(int i = arr.length/2 -1; i >= 0; i--){
            max_heapify(arr, i, arr.length-1);
        }
        return arr;
    }
}
