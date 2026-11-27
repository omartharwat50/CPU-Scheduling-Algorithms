import java.util.*;

public class Simulator {
    public static void main(String[] args) {
        List<Process> test1 = new ArrayList<>();
        test1.add(make("P1", 0, 4, 2));
        test1.add(make("P2", 1, 7, 3));
        test1.add(make("P3", 3, 2, 1));
        runTestCase("Test Case 1", test1, 3, 0);

        List<Process> test2 = new ArrayList<>();
        test2.add(make("P1", 0, 5, 4));
        test2.add(make("P2", 1, 6, 2));
        test2.add(make("P3", 4, 4, 3));
        runTestCase("Test Case 2", test2, 3, 1);

        List<Process> test3 = new ArrayList<>();
        test3.add(make("P1", 0, 10, 3));
        test3.add(make("P2", 1, 2, 1));
        test3.add(make("P3", 2, 8, 4));
        test3.add(make("P4", 4, 1, 2));
        test3.add(make("P5", 6, 3, 2));
        runTestCase("Test Case 3", test3, 3, 1);

        List<Process> test4 = new ArrayList<>();
        test4.add(make("P1", 0, 3, 1));
        test4.add(make("P2", 8, 6, 4));
        test4.add(make("P3", 9, 5, 1));
        test4.add(make("P4", 10, 7, 2));
        test4.add(make("P5", 11, 2, 3));
        runTestCase("Test Case 4", test4, 3, 1);
    }

    private static void runTestCase(String title, List<Process> processes, int quantum, int cs) {
        System.out.println("\n========== " + title + " (Q = " + quantum + ", CS = " + cs + ") ==========");

        PreemptiveSJF sjf = new PreemptiveSJF(cs, processes);
        sjf.schedule();
        printResults("Preemptive SJF", sjf);

        AdaptiveContainerScheduler acs = new AdaptiveContainerScheduler(processes, quantum, cs);
        acs.schedule();
        printResults("Adaptive Container Scheduler", acs);
    }

    private static void printResults(String name, Scheduler scheduler) {
        System.out.println("\n" + name + " Timeline:");
        for (TimelineEntry e : scheduler.getTimeline())
            System.out.println(e);

        System.out.println("\nProcess\tArrival\tBurst\tFinish\tWaiting\tTurnaround\tResponse");
        for (Process p : scheduler.getProcesses()) {
            System.out.println(p.getName() + "\t" + p.getArrivalTime() + "\t" + p.getBurstTime() + "\t"
                    + p.getFinishTime() + "\t" + p.getWaitingTime() + "\t" + p.getTurnaroundTime() + "\t\t"
                    + p.getResponseTime());
        }

        System.out.println("\nAverage Waiting Time: " + round(scheduler.getAverageWaitingTime()));
        System.out.println("Average Turnaround Time: " + round(scheduler.getAverageTurnaroundTime()));
        System.out.println("Average Response Time: " + round(scheduler.getAverageResponseTime()));
    }

    private static double round(double value) {
        return Math.round(value * 100) / 100.0;
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
