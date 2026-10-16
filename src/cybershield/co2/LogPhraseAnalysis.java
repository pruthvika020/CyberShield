package cybershield.co2;

import java.util.Arrays;

public final class LogPhraseAnalysis {
    private LogPhraseAnalysis() { }

    // Suffix array via prefix doubling; LCP via Kasai.
    public static void analyze(String text) {
        if (text == null || text.length() < 2) {
            System.out.println("Not enough log text to analyze.");
            return;
        }
        int n = text.length();
        int[] sa = suffixArray(text);
        int[] lcp = kasai(text, sa);
        int best = 0, bestIndex = -1;
        for (int i = 1; i < n; i++) {
            if (lcp[i] > best) {
                best = lcp[i];
                bestIndex = sa[i];
            }
        }
        System.out.println("Combined log text length: " + n);
        if (best > 0 && bestIndex >= 0) {
            String phrase = text.substring(bestIndex, Math.min(n, bestIndex + best));
            System.out.println("Longest repeated substring: \"" + phrase + "\"");
            System.out.println("Repeated substring length : " + best);
        } else {
            System.out.println("No repeated substring found.");
        }
        System.out.println("Suffix array entries      : " + sa.length);
    }

    private static int[] suffixArray(String s) {
        int n = s.length();
        Integer[] order = new Integer[n];
        int[] rank = new int[n];
        int[] nextRank = new int[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
            rank[i] = s.charAt(i);
        }
        for (int k = 1; k < n; k *= 2) {
            final int step = k;
            Arrays.sort(order, (a, b) -> {
                if (rankValue(rank, a) != rankValue(rank, b))
                    return Integer.compare(rankValue(rank, a), rankValue(rank, b));
                return Integer.compare(a + step < n ? rank[a + step] : -1,
                                       b + step < n ? rank[b + step] : -1);
            });
            nextRank[order[0]] = 0;
            for (int i = 1; i < n; i++) {
                int a = order[i - 1], b = order[i];
                boolean different = rank[a] != rank[b]
                    || (a + k < n ? rank[a + k] : -1) != (b + k < n ? rank[b + k] : -1);
                nextRank[b] = nextRank[a] + (different ? 1 : 0);
            }
            System.arraycopy(nextRank, 0, rank, 0, n);
            if (rank[order[n - 1]] == n - 1) break;
            if (k > n / 2) break;
        }
        int[] sa = new int[n];
        for (int i = 0; i < n; i++) sa[i] = order[i];
        return sa;
    }

    private static int rankValue(int[] rank, int index) {
        return rank[index];
    }

    private static int[] kasai(String s, int[] sa) {
        int n = s.length();
        int[] inverse = new int[n];
        for (int i = 0; i < n; i++) inverse[sa[i]] = i;
        int[] lcp = new int[n];
        int h = 0;
        for (int i = 0; i < n; i++) {
            int r = inverse[i];
            if (r == 0) continue;
            int j = sa[r - 1];
            while (i + h < n && j + h < n && s.charAt(i + h) == s.charAt(j + h)) h++;
            lcp[r] = h;
            if (h > 0) h--;
        }
        return lcp;
    }
}
