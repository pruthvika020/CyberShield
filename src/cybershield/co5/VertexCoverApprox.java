package cybershield.co5;

import java.util.*;

public final class VertexCoverApprox {
    private VertexCoverApprox() { }

    // Standard 2-approximation: select both endpoints of each uncovered edge.
    public static Set<String> approximate(List<String[]> edges) {
        Set<String> cover = new LinkedHashSet<>();
        for (String[] edge : edges) {
            if (!cover.contains(edge[0]) && !cover.contains(edge[1])) {
                cover.add(edge[0]);
                cover.add(edge[1]);
            }
        }
        return cover;
    }
}
