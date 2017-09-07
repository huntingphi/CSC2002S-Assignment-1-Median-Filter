/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jethro
 */
public class MedianFilterTest {
    static final ForkJoinPool fjPool = new ForkJoinPool();
    static int filterWindow;
    static int seqThresh;
    static double[] inputFull;
    static double[] inputHalf;
    static double[] inputQuarter;
    static double[] inputThousand;
    static double[] input7;

    static double[] exp7;
    static double[] expFull;
    static double[] expHalf;
    static double[] expQuarter;
    static double[] expThousand;
    static final int MILLION = 1000000;
        static final int HALF_MILLION = 500000;
        static final int QUARTER_MILLION = 250000;
               static  final int THOUSAND = 1000;
   public static double[] populateArr(String path, int arrSize) throws IOException {
        double[] out;
        Scanner in = new Scanner(new File(path));
        int fileSize = in.nextInt();
        if(arrSize==fileSize){
        out = new double[fileSize];}else{
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
    public MedianFilterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws IOException {
        seqThresh = 1000;
filterWindow =21;
String path = "sampleinputfile.txt";
        inputFull = populateArr(path, MILLION);
        inputHalf = populateArr(path, HALF_MILLION);
                inputQuarter = populateArr(path, QUARTER_MILLION);
                inputThousand = populateArr(path, THOUSAND);
                input7 = populateArr(path, 7);
                Algorithm a = new Algorithm(inputFull,0,inputFull.length,filterWindow);
                a.filter();
                expFull = Arrays.copyOf(a.getArr(),a.getArr().length);
                a = new Algorithm(inputHalf,0,inputHalf.length,filterWindow);
                a.filter();
        expHalf = Arrays.copyOf(a.getArr(),a.getArr().length);
        a = new Algorithm(inputQuarter,0,inputQuarter.length,filterWindow);
                a.filter();
                expQuarter = Arrays.copyOf(a.getArr(),a.getArr().length);
                a = new Algorithm(inputThousand,0,inputThousand.length,filterWindow);
                a.filter();
                expThousand = Arrays.copyOf(a.getArr(),a.getArr().length);
                a = new Algorithm(input7,0,input7.length,filterWindow);
                a.filter();
                exp7 = Arrays.copyOf(a.getArr(),a.getArr().length);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of compute method, of class AlgorithmThreadAction.
     */
    

    /**
     * Test of getArr method, of class ParallelFilter.
     */
   @Test
    public void testComputeFULL() {
        System.out.println("compute");
        ParallelFilter instance = new ParallelFilter(inputFull,seqThresh,filterWindow);
        
        double[] expResult = expFull;
        fjPool.invoke(instance);
        double [] result = instance.getArr();
        assertArrayEquals(expResult, result,0.0000000000001);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    @Test
    public void testComputeHalf() {
        System.out.println("compute");
        ParallelFilter instance = new ParallelFilter(inputHalf, seqThresh, filterWindow);
        
        double[] expResult = expHalf;
         fjPool.invoke(instance);
         double[] result = instance.getArr();
        assertArrayEquals(expResult, result,0.0000000000001);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    @Test
    public void testComputeQuarter() {
        System.out.println("compute");
        ParallelFilter instance = new ParallelFilter(inputQuarter, seqThresh, filterWindow);
        
        double[] expResult = expQuarter;
        fjPool.invoke(instance);
        double[] result = instance.getArr();
        assertArrayEquals(expResult, result,0.0000000000001);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    @Test
    public void testComputeThousand() {
        System.out.println("compute");
        ParallelFilter instance = new ParallelFilter(inputThousand, seqThresh, filterWindow);
        
        double[] expResult = expThousand;
        fjPool.invoke(instance);
        double[] result = instance.getArr();
        assertArrayEquals(expResult, result,0.0000000000001);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    @Test
    public void testCompute7() {
        System.out.println("compute");
        ParallelFilter instance = new ParallelFilter(input7, seqThresh, filterWindow);
        
        double[] expResult = exp7;
//        for (double d : expResult) {
//            System.out.println(d);
//        }
double result[] = instance.getArr();
        fjPool.invoke(instance);
        assertArrayEquals(expResult, result,0.0000000000001);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
