package cybershield.co1;

import cybershield.model.SecurityEvent;
import java.util.ArrayList;
import java.util.List;

public final class SignatureSearch {
    private SignatureSearch() { }

    // KMP: searches for one signature in each event's signature field.
    public static List<SecurityEvent> searchKmp(List<SecurityEvent> events, String pattern) {
        List<SecurityEvent> matches = new ArrayList<>();
        if (pattern == null || pattern.isEmpty()) return matches;
        for (SecurityEvent event : events) {
            if (containsKmp(event.getSignature(), pattern)) matches.add(event);
        }
        return matches;
    }

    private static boolean containsKmp(String text, String pattern) {
        int[] lps = buildLps(pattern);
        int i = 0, j = 0;
        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++; j++;
                if (j == pattern.length()) return true;
            } else if (j != 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        return false;
    }

    private static int[] buildLps(String pattern) {
        int[] lps = new int[pattern.length()];
        int len = 0, i = 1;
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                lps[i++] = ++len;
            } else if (len != 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }
        return lps;
    }

    // Aho-Corasick-style multi-pattern demonstration using trie failure links is
    // intentionally kept separate from KMP in a later extension; this method
    // reports all distinct signatures found in the loaded event records.
    public static List<String> listKnownSignatures(List<SecurityEvent> events) {
        List<String> signatures = new ArrayList<>();
        for (SecurityEvent event : events) {
            if (!signatures.contains(event.getSignature())) signatures.add(event.getSignature());
        }
        return signatures;
    }
}
