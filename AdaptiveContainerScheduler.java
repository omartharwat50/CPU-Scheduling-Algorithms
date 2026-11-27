import java.util.*;

public class AdaptiveContainerScheduler extends Scheduler {
    private int quantumTime;
    private int agingInterval = 4;
    private Queue<Process> tier1Queue;
    private PriorityQueue<Process> tier2Queue;

    public AdaptiveContainerScheduler(List<Process> processList, int quantum, int csTime) {
        super(csTime, processList);
        quantumTime = quantum;
        tier1Queue = new LinkedList<>();
        tier2Queue = new PriorityQueue<>((a, b) -> {
            if (a.getEffectivePriority() != b.getEffectivePriority())
                return a.getEffectivePriority() - b.getEffectivePriority();
            if (a.getTier2WaitStartTime() != b.getTier2WaitStartTime())
                return a.getTier2WaitStartTime() - b.getTier2WaitStartTime();
            return a.getName().compareTo(b.getName());
        });
    }

    @Override
    public void schedule() {
        timeline.clear();
        tier1Queue.clear();
        tier2Queue.clear();
        for (Process p : processes) {
            p.setRemainingTime(p.getBurstTime());
            p.setFinishTime(0);
            p.setResponseTime(-1);
            p.setTier(1);
            p.setBatchJob(false);
            p.setEffectivePriority(p.getInitialPriority());
        }

        int time = 0;
        int completed = 0;
        int quantumUsed = 0;
        int csRemaining = 0;
        Process current = null;
        Process previous = null;
        Process next = null;

        System.out.println("\nACS Heuristic Demotion Matrix (Q = " + quantumTime + ", CS = " + contextSwitchTime + ")");

        while (completed < processes.size()) {
            for (Process p : processes) {
                if (p.getArrivalTime() == time)
                    tier1Queue.add(p);
            }

            if (current == null && next == null) {
                Process chosen;
                if (!tier1Queue.isEmpty())
                    chosen = tier1Queue.poll();
                else
                    chosen = selectNextTier2Process(time);

                if (chosen != null) {
                    if (previous != null && previous != chosen && contextSwitchTime > 0) {
                        next = chosen;
                        csRemaining = contextSwitchTime;
                    } else {
                        current = chosen;
                        quantumUsed = 0;
                        if (current.getResponseTime() == -1)
                            current.setResponseTime(time - current.getArrivalTime());
                    }
                }
            }

            if (next != null) {
                String cs = "CS: " + previous.getName() + " -> " + next.getName();
                outputHeuristicDemotionMatrix(time, cs);
                addToTimeline(time, cs);
                time++;
                csRemaining--;
                if (csRemaining == 0) {
                    current = next;
                    next = null;
                    quantumUsed = 0;
                    if (current.getResponseTime() == -1)
                        current.setResponseTime(time - current.getArrivalTime());
                }
                continue;
            }

            if (current == null) {
                outputHeuristicDemotionMatrix(time, "IDLE");
                addToTimeline(time, "IDLE");
                previous = null;
                time++;
                continue;
            }

            outputHeuristicDemotionMatrix(time, current.getName() + " (Tier " + current.getTier() + ")");
            addToTimeline(time, current.getName());
            current.setRemainingTime(current.getRemainingTime() - 1);
            quantumUsed++;
            time++;

            if (current.getRemainingTime() == 0) {
                current.setFinishTime(time);
                completed++;
                previous = current;
                current = null;
            } else if (current.getTier() == 1 && quantumUsed == quantumTime) {
                demoteToTier2(current, time);
                previous = current;
                current = null;
            }
        }
        outputHeuristicDemotionMatrix(time, "Done");
        calculateMetrics();
    }

    public void demoteToTier2(Process p, int currentTime) {
        p.setTier(2);
        p.setBatchJob(true);
        p.setTier2WaitStartTime(currentTime);
        p.setEffectivePriority(p.getInitialPriority());
        tier2Queue.add(p);
    }

    public void applyAging(int currentTime) {
        List<Process> temp = new ArrayList<>(tier2Queue);
        tier2Queue.clear();
        for (Process p : temp) {
            int waited = currentTime - p.getTier2WaitStartTime();
            int newPriority = p.getInitialPriority() - waited / agingInterval;
            if (newPriority < 1)
                newPriority = 1;
            p.setEffectivePriority(newPriority);
            tier2Queue.add(p);
        }
    }

    public Process selectNextTier2Process(int currentTime) {
        applyAging(currentTime);
        return tier2Queue.poll();
    }

    public void outputHeuristicDemotionMatrix(int time, String running) {
        applyAging(time);

        String tier1 = "";
        for (Process p : tier1Queue) {
            if (!tier1.isEmpty())
                tier1 += ", ";
            tier1 += p.getName() + " (remaining: " + p.getRemainingTime() + ", Priority: " + p.getInitialPriority() + ")";
        }
        if (tier1.isEmpty())
            tier1 = "Empty";

        String tier2 = "";
        PriorityQueue<Process> copy = new PriorityQueue<>(tier2Queue);
        while (!copy.isEmpty()) {
            Process p = copy.poll();
            if (!tier2.isEmpty())
                tier2 += ", ";
            tier2 += p.getName() + " (remaining: " + p.getRemainingTime() + ", Priority: " + p.getEffectivePriority() + ")";
        }
        if (tier2.isEmpty())
            tier2 = "Empty";

        System.out.println("Time " + time + "\t" + running + "\tTier 1: [" + tier1 + "]\tTier 2: [" + tier2 + "]");
    }

    private void addToTimeline(int time, String label) {
        if (!timeline.isEmpty()) {
            TimelineEntry last = timeline.get(timeline.size() - 1);
            if (last.getLabel().equals(label) && last.getEndTime() == time) {
                timeline.set(timeline.size() - 1, new TimelineEntry(last.getStartTime(), time + 1, label));
                return;
            }
        }
        timeline.add(new TimelineEntry(time, time + 1, label));
    }
}
