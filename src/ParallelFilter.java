
import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jethro
 */
public class ParallelFilter extends RecursiveAction {

    static int SEQ_THRESHOLD = 1;
    static double[] arr;
    static  double[] out;
    int lo;
    int hi;
    static int FilterWindow;
//    Algorithm a = new Algorithm();

    
      ParallelFilter( int lo, int hi) {
//        this.arr = arr;
        this.lo = lo;
        this.hi = hi;
//        SEQ_THRESHOLD = seq_threshold;
//        this.out = Arrays.copyOf(arr, arr.length);
//        a.setArray(arr);
//        a.setI(lo);
//        a.setF(hi);

    }
ParallelFilter(double[] arr, int seq_threshold,int window) {
    lo = 0;
    hi = arr.length;
    this.arr = arr;
//        this.lo = lo;
//        this.hi = hi;
        SEQ_THRESHOLD = seq_threshold;
//        this.out = Arrays.copyOf(arr, arr.length);
//        a.setArray(arr);
//        a.setI(lo);
//        a.setF(hi);
        out = new double[arr.length];
       FilterWindow = window;

    }

    @Override
    protected void compute() {
        if (hi - lo <= SEQ_THRESHOLD) {
            int buffer = FilterWindow / 2;
            int start;
            int end;
            if (lo < buffer) {
                start = buffer;
            } else {
                start = lo;
            }
            if (arr.length - hi < buffer) {
                end = arr.length - buffer;
            } else {
                end = hi;
            }
//            System.out.println("end:"+ end+" hi"+hi);
            for (int j = lo; j < hi; j++) {
                if (j < start || j+buffer>=arr.length) {
                    out[j] = arr[j];
//                                        System.out.println(j+" : From "+out[j] +" to "+ arr[j]);

                } else {
                    double[] sub = Arrays.copyOfRange(arr, j - FilterWindow / 2, j + FilterWindow / 2 + 1);
                    Arrays.sort(sub);
                    out[j] = sub[sub.length / 2];
//                    System.out.println(j+" : From "+out[j] +" to "+ sub[sub.length / 2]+" chose median from "+sub[0]+", "+sub[1]+", "+sub[2]);
                }
            }
        } else {
            ParallelFilter left = new ParallelFilter(lo, (hi + lo) / 2);//Arrays.copyOfRange(arr, lo,(lo + hi) / 2), lo, (lo + hi) / 2,FilterWindow);
            ParallelFilter right = new ParallelFilter((hi + lo) / 2, hi);//Arrays.copyOfRange(arr,(lo + hi) / 2,hi), (lo + hi) / 2,hi,FilterWindow);
            left.fork();
            right.compute();
            left.join();

        }

    }

    public double[] getArr() {
        return this.out;
    }

}
