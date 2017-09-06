
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jethro
 */
public class Algorithm {
    double [] array;
    int i;
    int f;
    int window;
    
    Algorithm(){}

    public Algorithm(double[] array, int i, int f, int window) {
        this.array = array;
        this.i = i;
        this.f = f;
        this.window = window;
    }

    public void setArray(double[] array) {
        this.array = array;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setWindow(int window) {
        this.window = window;
    }

//    public Algorithm(double[] arr) {
//        this.array = arr;
//    }
    
    
    
    
    
    void filter(){
        int start= i;
        int end = f;
       int buffer = window/2;
        
        if(i <buffer){
            start = buffer;
        }else{
        start =i;
    }
        if(array.length-f<buffer){
            end=array.length-buffer;
        }else{
            end = f;
        }
//        double [] out = Arrays.copyOf(array,array.length);
        for (int j = start; j < end; j++) {
        double [] sub = Arrays.copyOfRange(array, j-window/2,j+window/2+1);
        Arrays.sort(sub);
        array[j]= sub[sub.length/2];
        }
//        this.array = out;
    }
    
    public double [] getArr(){
        return array;
    }
    
}
