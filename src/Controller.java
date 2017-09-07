
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jethro
 */
public class Controller {
    String outputFile="";
    static double [] serArr;
        static double [] parArr;
    static final ForkJoinPool fjPool = new ForkJoinPool();
    static long startTime = 0;

    private static void tick() {
        startTime = System.currentTimeMillis();
    }

    private static float tock() {
        return (System.currentTimeMillis() - startTime) / 1000.0f;
    }
    double[] inputArr;
    int seqThresh;
    int filterWindow;
//String inputFile;

    void setSeqThresh(int seq_thresh) {
        seqThresh = seq_thresh;
    }

    void setFilterWindow(int window) {
        filterWindow = window;
    }

    void setInput(String input, int arrSize) throws IOException {
//        inputFile = input;

        inputArr = populateArr(input, arrSize);
    }

    double[] generateSeqArr() {
        Algorithm a;
        a = new Algorithm(inputArr, 0, inputArr.length, filterWindow);
        a.filter();
        serArr = Arrays.copyOf(a.getArr(), a.getArr().length);
        return serArr;
    }

    double[] generateParArr() {
        ParallelFilter instance = new ParallelFilter(inputArr, seqThresh, filterWindow);

        fjPool.invoke(instance);
        parArr = instance.getArr();
        return parArr;
    }

    float[] getDelta(float[] par, float[] seq) {
        float[] out = new float[par.length];
        for (int i = 0; i < par.length; i++) {
            float value = par[i] - seq[i];
            out[i] = value;
        }
        
        return out;
    }

     String[] toStringArr(float[] par) {
        String[] out = new String[par.length];
        for (int i = 0; i < par.length; i++) {
            Object value = par[i];
            out[i] = value + "";
        }
        return out;

    }

    double[] populateArr(String path, int arrSize) throws IOException {
        double[] out;
        Scanner in = new Scanner(new File(path));
        int fileSize = in.nextInt();
        if (arrSize == fileSize) {
            out = new double[fileSize];
        } else {
            out = new double[arrSize];
        }
        in.nextLine();
        for (int i = 0; in.hasNextLine() && i < arrSize; i++) {

            String s = in.nextLine();
//             System.out.println(s);
            out[i] = Double.parseDouble(s.substring(s.indexOf(" ")));
        }
        return out;
    }

    static void writeToFile(double[] arr, String name) throws IOException {
        File f = new File(name);
        f.createNewFile();
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(arr.length);
        bw.newLine();
        for (int i = 0; i < arr.length; i++) {
            bw.write(i + " " + arr[i]);
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    float[] getParTimes() throws IOException {
       float[] results = new float[6];
        double[] out = null;
        for (int i = 0; i < 6; i++) {
            tick();
            out = generateParArr();
            results[i] = tock();
        }
        if(outputFile.isEmpty()){writeToFile(out, "sampleoutput_parallel.txt");}else{writeToFile(out, outputFile);}
        return results;
    }

    float[] getSerialTimes() throws IOException {
        float[] results = new float[6];
        double[] out = null;
        for (int i = 0; i < 6; i++) {
            tick();
            out = generateSeqArr();
            results[i] = tock();
        }
        if(outputFile.isEmpty())writeToFile(out, "sampleoutput_serial.txt");
        return results;
    }

    void setOutputFile(String output) {
        this.outputFile = output;
    }

}
