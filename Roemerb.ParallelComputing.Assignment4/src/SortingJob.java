public class SortingJob {
    public int[] slice = {};
    public boolean kill = false;

    public SortingJob(int[] slice) {
        this.slice = slice;
    }

    public SortingJob(boolean kill) {
        this.kill = kill;
    }
}
