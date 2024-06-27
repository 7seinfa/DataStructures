/**
 * @author Hussein Abdallah
 */
public class DisjointSet {
    int n, parent[], rank[];
    boolean finalSet;

    public DisjointSet (int n) {
        this.n = n;
    }

    public void uandf (int n) {
        this.parent = new int[n];
        this.rank = new int[n];
        this.finalSet = false;
    }

    public void make_set (int i) {
        this.parent[i] = i;
    }

    public int find_set (int i) {
        if (this.finalSet == false) {
            if (this.parent[i] != i) {
                this.parent[i] = this.find_set(this.parent[i]);
                return this.parent[i];
            }
        }
        return i;
    }

    public void union_sets (int i, int j) {
        i = this.find_set(i);
        j = this.find_set(j);

        if (this.rank[i] > this.rank[j]) this.parent[j] = this.parent[i];
        else if (this.rank[i] < this.rank[j]) this.parent[j] = this.parent[i];
        else {
            this.parent[j] = this.parent[i];
            this.rank[i]++;
        }
    }

    public int final_sets() {
        for (int i = 1; i < this.parent.length; i++) {
            if (this.parent[i] != 0) {
                this.find_set(i);
            }
        }

        int cur = 1;
        for (int i = 1; i < this.parent.length; i++) {
            if (this.parent[i] == i) {
                this.parent[i] = cur;
                cur++;
                this.rank[i] = 1;
            } else this.rank[i] = 0;
        }

        for (int i = 1; i < this.parent.length; i++) {
            if (this.rank[i] == 0 && this.parent[i] > 0) this.parent[i] = this.parent[this.parent[i]];
        }

        this.finalSet = true;
        return cur-1;
    }
}
