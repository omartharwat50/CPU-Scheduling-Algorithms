import java.util.*;
public abstract class Scheduler {
 protected final int contextSwitchTime; protected final List<Process> processes;
 protected double averageWaitingTime, averageTurnaroundTime, averageResponseTime;
 protected final List<TimelineEntry> timeline = new ArrayList<>();
 public Scheduler(int cs, List<Process> ps){ if(cs<0) throw new IllegalArgumentException("CS cannot be negative"); contextSwitchTime=cs; processes=new ArrayList<>(ps); }
 public abstract void schedule();
 protected void calculateMetrics(){ if(processes.isEmpty()) return; long w=0,t=0,r=0; for(Process p:processes){int tat=p.getFinishTime()-p.getArrivalTime(); int wt=tat-p.getBurstTime(); p.setTurnaroundTime(tat); p.setWaitingTime(wt); w+=wt;t+=tat;r+=p.getResponseTime();} averageWaitingTime=(double)w/processes.size(); averageTurnaroundTime=(double)t/processes.size(); averageResponseTime=(double)r/processes.size(); }
 public List<Process> getProcesses(){return processes;} public List<TimelineEntry> getTimeline(){return timeline;}
 public double getAverageWaitingTime(){return averageWaitingTime;} public double getAverageTurnaroundTime(){return averageTurnaroundTime;} public double getAverageResponseTime(){return averageResponseTime;}
}
