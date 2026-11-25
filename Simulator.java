import java.util.*;

public class Simulator {
    public static void main(String[] args) {
        List<Process> ps = new ArrayList<>();
        ps.add(make("P1", 0, 4, 2));
        ps.add(make("P2", 1, 7, 3));
        ps.add(make("P3", 3, 2, 1));
        PreemptiveSJF sjf = new PreemptiveSJF(0, ps);
        sjf.schedule();
        System.out.println("=== SJF Timeline ===");
        for (TimelineEntry e : sjf.getTimeline())
            System.out.println(e);
        System.out.printf("\n%-8s %-7s %-6s %-7s %-7s %-11s %-8s%n", "Process", "Arrival", "Burst", "Finish", "Waiting",
                "Turnaround", "Response");
        for (Process p : sjf.getProcesses())
            System.out.printf("%-8s %-7d %-6d %-7d %-7d %-11d %-8d%n", p.getName(), p.getArrivalTime(),
                    p.getBurstTime(), p.getFinishTime(), p.getWaitingTime(), p.getTurnaroundTime(),
                    p.getResponseTime());
        System.out.printf("\nAvg Waiting: %.2f%nAvg Turnaround: %.2f%nAvg Response: %.2f%n",
                sjf.getAverageWaitingTime(), sjf.getAverageTurnaroundTime(), sjf.getAverageResponseTime());
    }

    private static Process make(String name, int arrival, int burst, int priority) {
        Process p = new Process(name);
        p.setArrivalTime(arrival);
        p.setBurstTime(burst);
        p.setRemainingTime(burst);
        p.setInitialPriority(priority);
        p.setEffectivePriority(priority);
        return p;
    }
}
