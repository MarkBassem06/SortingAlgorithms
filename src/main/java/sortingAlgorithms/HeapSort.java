package sortingAlgorithms;

public class HeapSort implements SortingAlg{
    @Override
    public int[] sort(int[] arr) {
        arr = build_max_heap(arr);
        int end = arr.length -1;
        while(end>0){
            int temp = arr[end];
            arr[end] = arr[0];
            arr[0] = temp;
            end--;
            max_heapify(arr,0, end);
        }
        return arr;
    }
    public void max_heapify(int[] arr, int idx, int end){
        int l = idx*2 +1;
        int r = idx*2 +2;
        int largest = idx;
        if(l <= end && arr[l] > arr[largest]){
            largest = l;
        }
        if(r <= end && arr[r] > arr[largest]){
            largest = r;
        }
        if(largest != idx){
            int temp = arr[idx];
            arr[idx] = arr[largest];
            arr[largest] = temp;
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
