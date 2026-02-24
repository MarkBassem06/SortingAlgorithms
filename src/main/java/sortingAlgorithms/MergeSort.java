package sortingAlgorithms;

public class MergeSort implements SortingAlg{
    @Override
    public int[] sort(int[] arr) {
        return divide(arr);
    }

    public int[] divide(int[] arr){
        int n = arr.length;
        if(n == 1){
            return arr;
        }
        int[] arrLeft = new int[n/2];
        int[] arrRight = new int[n-(n/2)];

        for(int i = 0; i<n/2;i++){
            arrLeft[i] = arr[i];
        }
        for(int i = n/2; i<n;i++){
            arrRight[i-(n/2)] = arr[i];
        }

        return merge(divide(arrLeft),divide(arrRight));
    }
    public int[] merge(int[] arrLeft, int[] arrRight){
        int[] result = new int[arrLeft.length + arrRight.length];
        int l = 0; int r = 0; int i = 0;
        while(l < arrLeft.length && r < arrRight.length){
            if(arrLeft[l] <= arrRight[r]){
                result[i] = arrLeft[l];
                l++; i++;
            }
            else{
                result[i] = arrRight[r];
                r++; i++;
            }
        }
        while(l < arrLeft.length){
            result[i] = arrLeft[l];
            l++; i++;
        }
        while(r < arrRight.length){
            result[i] = arrRight[r];
            r++; i++;
        }
        return result;
    }
}
