package cybershield.co4;

import java.util.*;

public final class MaxFlow {
    private MaxFlow() { }

    public static int calculate(List<String[]> edges, String source, String sink) {
        Map<String, Integer> ids = new LinkedHashMap<>();
        for (String[] e : edges) {
            ids.computeIfAbsent(e[0], k -> ids.size());
            ids.computeIfAbsent(e[1], k -> ids.size());
        }
        if (!ids.containsKey(source) || !ids.containsKey(sink)) return 0;
        int n = ids.size();
        int[][] capacity = new int[n][n];
        for (String[] e : edges) {
            int u = ids.get(e[0]), v = ids.get(e[1]);
            int cap = Integer.parseInt(e[2]);
            if (cap < 0) throw new IllegalArgumentException("Capacity cannot be negative.");
            capacity[u][v] += cap;
        }
        int s = ids.get(source), t = ids.get(sink), totalFlow = 0;
        int[] parent = new int[n];
        while (bfs(capacity, s, t, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = t; v != s; v = parent[v])
                pathFlow = Math.min(pathFlow, capacity[parent[v]][v]);
            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                capacity[u][v] -= pathFlow;
                capacity[v][u] += pathFlow;
            }
            totalFlow += pathFlow;
        }
        return totalFlow;
    }

    private static boolean bfs(int[][] residual, int s, int t, int[] parent) {
        Arrays.fill(parent, -1);
        boolean[] seen = new boolean[residual.length];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(s);
        seen[s] = true;
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int v = 0; v < residual.length; v++) {
                if (!seen[v] && residual[u][v] > 0) {
                    parent[v] = u;
                    seen[v] = true;
                    queue.add(v);
                }
            }
        }
        return seen[t];
    }
}
