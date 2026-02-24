package sortingAlgorithms;

public class SelectionSort implements SortingAlg{
    @Override
    public int[] sort(int[] arr){
        int n = arr.length;
        for(int i = 0; i < n-1; i++){
            int idx = i;
            for(int j = i+1; j<n;j++){
                if(arr[j] < arr[idx]){
                    idx = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[idx];
            arr[idx] = temp;
        }
        return arr;
    }
}
