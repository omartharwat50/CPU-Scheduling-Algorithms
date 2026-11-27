import java.util.*;

public class PreemptiveSJF extends Scheduler {
    public PreemptiveSJF(int cs, List<Process> ps) {
        super(cs, ps);
    }

    public void schedule() {
        timeline.clear();
        for (Process p : processes) {
            p.setRemainingTime(p.getBurstTime());
            p.setFinishTime(0);
            p.setWaitingTime(0);
            p.setTurnaroundTime(0);
            p.setResponseTime(-1);
        }
        int time = 0, done = 0;
        Process previous = null;
        while (done < processes.size()) {
            Process chosen = shortestReady(time);
            if (chosen == null) {
                append(time, time + 1, "IDLE");
                time++;
                previous = null;
                continue;
            }
            if (previous != null && previous != chosen && contextSwitchTime > 0) {
                append(time, time + contextSwitchTime, "CS: " + previous.getName() + " -> " + chosen.getName());
                time += contextSwitchTime;
                chosen = shortestReady(time);
                if (chosen == null) {
                    previous = null;
                    continue;
                }
            }
            if (chosen.getResponseTime() == -1)
                chosen.setResponseTime(time - chosen.getArrivalTime());
            append(time, time + 1, chosen.getName());
            chosen.setRemainingTime(chosen.getRemainingTime() - 1);
            time++;
            previous = chosen;
            if (chosen.getRemainingTime() == 0) {
                chosen.setFinishTime(time);
                done++;
            }
        }
        calculateMetrics();
    }

    private Process shortestReady(int time) {
        return processes.stream().filter(p -> p.getArrivalTime() <= time && p.getRemainingTime() > 0)
                .min(Comparator.comparingInt(Process::getRemainingTime).thenComparingInt(Process::getArrivalTime)
                        .thenComparing(Process::getName))
                .orElse(null);
    }

    private void append(int s, int e, String label) {
        if (s >= e)
            return;
        if (!timeline.isEmpty()) {
            TimelineEntry last = timeline.get(timeline.size() - 1);
            if (last.getLabel().equals(label) && last.getEndTime() == s) {
                timeline.set(timeline.size() - 1, new TimelineEntry(last.getStartTime(), e, label));
                return;
            }
        }
        timeline.add(new TimelineEntry(s, e, label));
    }
}
