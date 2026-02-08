import java.util.Locale;
import java.util.Random;

public class PrefixAveragesExperiment {

    // Algorithm 1: O(n^2)
    public static double[] prefixAverage1(double[] x) {
        int n = x.length;
        double[] a = new double[n];
        for (int j = 0; j < n; j++) {
            double total = 0;
            for (int i = 0; i <= j; i++) {
                total += x[i];
            }
            a[j] = total / (j + 1);
        }
        return a;
    }

    // Algorithm 2: O(n)
    public static double[] prefixAverage2(double[] x) {
        int n = x.length;
        double[] a = new double[n];
        double total = 0;
        for (int j = 0; j < n; j++) {
            total += x[j];
            a[j] = total / (j + 1);
        }
        return a;
    }

    private static double[] randomArray(int n, long seed) {
        Random r = new Random(seed);
        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = r.nextDouble();
        }
        return x;
    }

    private static long timeNanos(Runnable task, int repeats) {
        // small warm-up
        task.run();

        long best = Long.MAX_VALUE;
        for (int i = 0; i < repeats; i++) {
            long start = System.nanoTime();
            task.run();
            long end = System.nanoTime();
            long elapsed = end - start;
            if (elapsed < best) best = elapsed;
        }
        return best;
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        // Representative input sizes (similar idea to StringExperiment: grow n gradually)
        int[] sizes = {100, 200, 400, 800, 1600, 3200, 6400, 12800};

        // Repeat counts: keep prefixAverage1 lower because it grows fast
        int repeatsPA1 = 3;
        int repeatsPA2 = 10;

        System.out.println("n, prefixAverage1_ms, prefixAverage2_ms");

        for (int n : sizes) {
            double[] x = randomArray(n, 12345L);

            long t1 = timeNanos(() -> prefixAverage1(x), repeatsPA1);
            long t2 = timeNanos(() -> prefixAverage2(x), repeatsPA2);

            double ms1 = t1 / 1_000_000.0;
            double ms2 = t2 / 1_000_000.0;

            System.out.printf("%d, %.3f, %.3f%n", n, ms1, ms2);
        }

        System.out.println();
        System.out.println("Tip: You can copy the CSV output into Excel to make a log-log chart (optional).");
    }
}
