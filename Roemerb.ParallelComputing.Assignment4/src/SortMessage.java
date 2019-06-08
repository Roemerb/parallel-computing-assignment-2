import java.io.Serializable;

public class SortMessage implements Serializable
{
    private String jobId;
    private int[] arr;
    private int part;
    private int totalParts;

    public String getJobId()
    {
        return jobId;
    }

    public void setJobId(String jobId)
    {
        this.jobId = jobId;
    }

    public int[] getArr()
    {
        return arr;
    }

    public void setArr(int[] arr)
    {
        this.arr = arr;
    }

    public int getPart()
    {
        return part;
    }

    public void setPart(int part)
    {
        this.part = part;
    }

    public int getTotalParts()
    {
        return totalParts;
    }

    public void setTotalParts(int totalParts)
    {
        this.totalParts = totalParts;
    }
}
