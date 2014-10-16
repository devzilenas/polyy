class DisjointSets {
	public static int[] createSets(int size) {
		int[] p = new int[size];
		for (int i = 0; i < size; i++) {
			p[i] = i;
		}
		return p;
	}
	/** Finds root of p */
	public static int root(int[] p, int x) {
		return x == p[x] ? x : (p[x] = root(p, p[x]));
	}

	public static void unite(int[] p, int a, int b) {
		a = root(p, a);
		b = root(p, b);
		if (a!=b) {
			p[a] = b;
		}
	}
}
