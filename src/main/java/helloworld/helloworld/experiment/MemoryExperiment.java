package helloworld.helloworld.experiment;

import java.util.ArrayList;
import java.util.List;

/**
 * Runs JVM memory experiments for heap and stack limits.
 *
 * This class is separate from the game and is used to observe
 * how many objects or recursive calls can be created before the JVM
 * throws OutOfMemoryError or StackOverflowError.
 *
 * @author Rama Houreh
 */
public class MemoryExperiment {

    private static byte[] reserveMemory = new byte[10_000_000];
    private static int lastDepth = 0;

    /**
     * Entry point for running experiments independently.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
//         runHeapExperiment();
        runStackExperiment();
    }

    /**
     * Repeatedly creates large arrays and stores them in a list
     * until heap memory is exhausted.
     * Prints:
     * - total objects created
     * - elapsed time
     * - progress every 100 objects
     */
    public static void runHeapExperiment() {
        List<long[]> objects = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        int count = 0;

        try {
            while (true) {
                long[] block = new long[50000];
                objects.add(block);
                count++;

                if (count % 100 == 0) {
                    long elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000;
                    System.out.println("Heap objects created: " + count +
                            " | Elapsed time: " + elapsedSeconds + " seconds");
                }
            }
        } catch (OutOfMemoryError e) {
            reserveMemory = null;
            objects.clear();

            long elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000;
            System.out.println("OutOfMemoryError reached.");
            System.out.println("Total heap objects created: " + count);
            System.out.println("Time elapsed: " + elapsedSeconds + " seconds");
        }
    }

    /**
     * Repeatedly calls itself until stack memory is exhausted
     * Prints:
     * - recursion depth
     * - elapsed time
     * - progress every 1000 calls
     */
    public static void runStackExperiment() {
        long startTime = System.currentTimeMillis();
        lastDepth = 0;

        try {
            recursiveCall(0, startTime);
        } catch (StackOverflowError e) {
            long elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000;
            System.out.println("StackOverflowError reached.");
            System.out.println("Maximum stack depth reached: " + lastDepth);
            System.out.println("Time elapsed: " + elapsedSeconds + " seconds");
        }
    }

    /**
     * Recursive helper method used for the stack experiment.
     *
     * @param depth current recursion depth
     * @param startTime experiment start time in milliseconds
     */
    private static void recursiveCall(int depth, long startTime) {
        lastDepth = depth;

        if (depth % 1000 == 0) {
            long elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000;
            System.out.println("Stack depth: " + depth +
                    " | Elapsed time: " + elapsedSeconds + " seconds");
        }

        recursiveCall(depth + 1, startTime);
    }
}