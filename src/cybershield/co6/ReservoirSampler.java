package cybershield.co6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class ReservoirSampler {
    private ReservoirSampler() { }

    public static <T> List<T> sample(List<T> stream, int sampleSize, Random random) {
        if (sampleSize < 0) throw new IllegalArgumentException("Sample size cannot be negative.");
        int k = Math.min(sampleSize, stream.size());
        List<T> reservoir = new ArrayList<>(k);
        for (int i = 0; i < stream.size(); i++) {
            if (i < k) {
                reservoir.add(stream.get(i));
            } else if (k > 0) {
                int j = random.nextInt(i + 1);
                if (j < k) reservoir.set(j, stream.get(i));
            }
        }
        return reservoir;
    }
}
