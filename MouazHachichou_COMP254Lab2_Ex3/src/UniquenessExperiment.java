import java.util.Locale;
import java.util.Random;

public class UniquenessExperiment {

    // unique1: O(n^2)
    public static boolean unique1(int[] data) {
        int n = data.length;
        for (int j = 0; j < n - 1; j++) {
            for (int k = j + 1; k < n; k++) {
                if (data[j] == data[k]) return false;
            }
        }
        return true;
    }

    // unique2: O(n log n)  (sort + linear scan)
    public static boolean unique2(int[] data) {
        int[] copy = data.clone();
        java.util.Arrays.sort(copy);
        for (int i = 1; i < copy.length; i++) {
            if (copy[i] == copy[i - 1]) return false;
        }
        return true;
    }

    private static int[] randomArrayWithLowDuplicates(int n, long seed) {
        // To avoid "early exit" due to duplicates, we generate values from a large range.
        Random r = new Random(seed);
        int[] x = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = r.nextInt(Integer.MAX_VALUE);
        }
        return x;
    }

    private static long timeMillis(Runnable task) {
        // Warm-up (helps stabilize JIT)
        task.run();

        long start = System.nanoTime();
        task.run();
        long end = System.nanoTime();

        return (end - start) / 1_000_000;
    }

    private static int findMaxNUnderOneMinute(String name, java.util.function.IntFunction<Long> timeFn) {
        final long LIMIT_MS = 60_000;

        int low = 0;
        int high = 1;

        // 1) Exponential search to find an upper bound where it exceeds the limit
        while (true) {
            long t = timeFn.apply(high);
            System.out.printf("%s probe n=%d -> %d ms%n", name, high, t);

            if (t > LIMIT_MS) break;
            low = high;

            // prevent runaway for very fast algorithms
            if (high >= 2_000_000) {
                System.out.printf("%s reached safety cap at n=%d%n", name, high);
                return high;
            }

            high *= 2;
        }

        // 2) Binary search between low (<=limit) and high (>limit)
        while (low + 1 < high) {
            int mid = low + (high - low) / 2;
            long t = timeFn.apply(mid);
            System.out.printf("%s check n=%d -> %d ms%n", name, mid, t);

            if (t <= LIMIT_MS) low = mid;
            else high = mid;
        }

        return low;
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        System.out.println("Finding max n such that runtime <= 60,000 ms (1 minute)");
        System.out.println("Note: times vary by machine; results are experimental.");

        // Timing function for unique1
        java.util.function.IntFunction<Long> timeUnique1 = (n) -> {
            int[] a = randomArrayWithLowDuplicates(n, 12345L);
            return timeMillis(() -> unique1(a));
        };

        // Timing function for unique2
        java.util.function.IntFunction<Long> timeUnique2 = (n) -> {
            int[] a = randomArrayWithLowDuplicates(n, 12345L);
            return timeMillis(() -> unique2(a));
        };

        System.out.println();
        int max1 = findMaxNUnderOneMinute("unique1", timeUnique1);
        System.out.println();
        int max2 = findMaxNUnderOneMinute("unique2", timeUnique2);

        System.out.println();
        System.out.println("RESULTS (largest n with runtime <= 1 minute):");
        System.out.printf("unique1 max n = %d%n", max1);
        System.out.printf("unique2 max n = %d%n", max2);
    }
}
