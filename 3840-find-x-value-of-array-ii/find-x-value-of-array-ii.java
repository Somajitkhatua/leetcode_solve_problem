class Solution {

    class Node {
        int product;
        int[] count;

        Node(int k) {
            count = new int[k];
        }
    }

    int k;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.k = k;

        int n = nums.length;
        Node[] tree = new Node[4 * n];

        build(tree, nums, 1, 0, n - 1);

        int[] result = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {

            int index = queries[q][0];
            int value = queries[q][1];
            int start = queries[q][2];
            int x = queries[q][3];

            // Update nums[index]
            update(tree, 1, 0, n - 1, index, value);

            // Query [start, n - 1]
            Node ans = query(tree, 1, 0, n - 1, start, n - 1);

            result[q] = ans.count[x];
        }

        return result;
    }

    // Build segment tree
    void build(Node[] tree, int[] nums, int node, int left, int right) {

        if (left == right) {
            tree[node] = new Node(k);

            int rem = nums[left] % k;

            tree[node].product = rem;
            tree[node].count[rem] = 1;

            return;
        }

        int mid = left + (right - left) / 2;

        build(tree, nums, node * 2, left, mid);
        build(tree, nums, node * 2 + 1, mid + 1, right);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // Update one position
    void update(Node[] tree, int node, int left, int right,
                int index, int value) {

        if (left == right) {

            tree[node] = new Node(k);

            int rem = value % k;

            tree[node].product = rem;
            tree[node].count[rem] = 1;

            return;
        }

        int mid = left + (right - left) / 2;

        if (index <= mid) {
            update(tree, node * 2, left, mid, index, value);
        } else {
            update(tree, node * 2 + 1, mid + 1, right, index, value);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // Query a range
    Node query(Node[] tree, int node, int left, int right,
               int ql, int qr) {

        if (ql <= left && right <= qr) {
            return tree[node];
        }

        int mid = left + (right - left) / 2;

        if (qr <= mid) {
            return query(tree, node * 2, left, mid, ql, qr);
        }

        if (ql > mid) {
            return query(tree, node * 2 + 1, mid + 1, right, ql, qr);
        }

        Node leftNode = query(tree, node * 2, left, mid, ql, qr);
        Node rightNode = query(tree, node * 2 + 1, mid + 1, right, ql, qr);

        return merge(leftNode, rightNode);
    }

    // Merge two consecutive segments
    Node merge(Node a, Node b) {

        Node res = new Node(k);

        // Product of entire segment
        res.product = (a.product * b.product) % k;

        // Prefixes completely inside left segment
        for (int r = 0; r < k; r++) {
            res.count[r] += a.count[r];
        }

        // Prefixes that continue into right segment
        for (int r = 0; r < k; r++) {

            if (b.count[r] == 0) {
                continue;
            }

            int newRemainder = (a.product * r) % k;

            res.count[newRemainder] += b.count[r];
        }

        return res;
    }
}