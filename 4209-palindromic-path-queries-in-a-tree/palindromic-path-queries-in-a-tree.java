
import java.util.*;

class Solution {
    private List<Integer>[] graph;
    private int[] tin, tout, depth, parent, order;
    private int[][] up;
    private int timer, log;
    private Fenwick bit;

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        parent = new int[n];
        order = new int[n];

        log = 1;
        while ((1L << log) <= n) log++;
        up = new int[log][n];

        // Iterative DFS
        boolean[] visited = new boolean[n];
        int[] stack = new int[n];
        int top = 0;
        stack[top++] = 0;
        visited[0] = true;
        parent[0] = 0;
        timer = 0;

        while (top > 0) {
            int u = stack[--top];
            tin[u] = timer;
            order[timer++] = u;

            for (int v : graph[u]) {
                if (visited[v]) continue;
                visited[v] = true;
                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[top++] = v;
            }
        }

        for (int i = 0; i < n; i++)
            up[0][i] = parent[i];

        for (int k = 1; k < log; k++)
            for (int u = 0; u < n; u++)
                up[k][u] = up[k - 1][up[k - 1][u]];

        int[] size = new int[n];
        Arrays.fill(size, 1);

        for (int i = n - 1; i > 0; i--) {
            int u = order[i];
            size[parent[u]] += size[u];
        }

        for (int u = 0; u < n; u++)
            tout[u] = tin[u] + size[u] - 1;

        char[] chars = s.toCharArray();
        bit = new Fenwick(n);

        // XOR difference structure for subtree updates
        for (int u = 0; u < n; u++) {
            int mask = 1 << (chars[u] - 'a');
            rangeXor(tin[u], tout[u], mask);
        }

        List<Boolean> answer = new ArrayList<>();

        for (String query : queries) {
            String[] p = query.split(" ");

            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);

                if (chars[u] != c) {
                    int delta =
                        (1 << (chars[u] - 'a'))
                        ^ (1 << (c - 'a'));

                    rangeXor(tin[u], tout[u], delta);
                    chars[u] = c;
                }
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);
                int w = lca(u, v);

                int mask = bit.query(tin[u] + 1)
                         ^ bit.query(tin[v] + 1)
                         ^ (1 << (chars[w] - 'a'));

                answer.add((mask & (mask - 1)) == 0);
            }
        }

        return answer;
    }

    private int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int k = 0; k < log; k++)
            if ((diff & (1 << k)) != 0)
                u = up[k][u];

        if (u == v) return u;

        for (int k = log - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }

        return parent[u];
    }

    private void rangeXor(int l, int r, int value) {
        bit.update(l + 1, value);
        bit.update(r + 2, value);
    }

    private static class Fenwick {
        private final int[] tree;
        private final int n;

        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 2];
        }

        void update(int i, int value) {
            while (i <= n + 1) {
                tree[i] ^= value;
                i += i & -i;
            }
        }

        int query(int i) {
            int result = 0;
            while (i > 0) {
                result ^= tree[i];
                i -= i & -i;
            }
            return result;
        }
    }
}
