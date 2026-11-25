import java.util.List;

public abstract class Schedular {
    protected int CStime;
    protected List<Process> processList;
    protected int AvgWaitingTime;
    protected int AvgTurnaroundTime;

    public Schedular(List<Process> processList, int CStime) {
        this.processList = processList;
        this.CStime = CStime;
    }
    public abstract void schedule();
    public void calculateMetrics(){};
    
    
}
