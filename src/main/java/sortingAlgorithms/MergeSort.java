package sortingAlgorithms;

public class MergeSort implements SortingAlg {
    private SortingCompCalculate calculate = new SortingCompCalculate();
    private SortingListener listener;
    @Override
    public SortingCompCalculate getCalculation() {
        return calculate;
    }

    @Override
    public void setListener(SortingListener listener) {
        this.listener = listener;
    }

    @Override
    public int[] sort(int[] arr) {
        calculate.reset();
        mergeSort(arr, 0, arr.length - 1);
        return arr;
    }

    private void mergeSort(int[] arr, int l, int r) {
        if (l >= r)
            return;

        int mid = (l+r) /2;

        mergeSort(arr, l, mid);
        mergeSort(arr, mid+1, r);

        merge(arr, l, mid, r);
    }

    private void merge(int[] arr, int l, int mid, int r) {

        int[] temp = new int[r-l+1];

        int i = l;
        int j = mid+1;
        int k = 0;

        while (i <= mid && j <= r) {
            calculate.addComparisons();
            if (listener != null) {
                listener.onCompare(arr, i, j);
            }
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= r) {
            temp[k++] = arr[j++];
        }

        //Back to original array
        for (int z = 0; z < temp.length; z++) {
            arr[l+z] = temp[z];
            calculate.addSwaps();
            if (listener != null)
                listener.onSwap(arr, l+z, l+z); //overwite same element
        }
    }
}