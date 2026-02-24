package sortingAlgorithms;

public class BubbleSort implements SortingAlg{
    @Override
    public int[] sort(int[] arr){
        int n = arr.length;
        for(int i = 0; i < n; i++){
            for(int j = 0; j< n-i-1;j++){
                if(arr[j+1] < arr[j]){
                    int temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }
}
