import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AdaptiveContainerSchedular extends Schedular {
    private int QuantamTime = 2;
    private Queue<Process> processQueue;
    private PriorityQueue<Process> priorityQueue;
    private int currentTime;

    public void demoteToTier2(Process process) {
        process.setTier(2);
        process.setTier2WaitStartTime(currentTime + 1);
        priorityQueue.add(process);
    }

    public AdaptiveContainerSchedular(List<Process> processList, int quantamTime, int CStime) {
        super(processList, CStime);
        this.QuantamTime = quantamTime;
        this.processQueue = new LinkedList<>();
        this.priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getEffectivePriority));
    }

    @Override
    public void schedule() {
        currentTime = 0;
        int completed = 0;
        Process currentProcess = null;
        int csRemaining = 0;

        while (completed < processList.size()) {
            for (Process process : processList) {
                if (process.getArrivalTime() == currentTime) {
                    process.setTier(1);
                    processQueue.add(process);
                }
            }

            if (csRemaining > 0) {
                csRemaining--;
                currentTime++;
                continue;
            }

            if (currentProcess == null && !processQueue.isEmpty()) {
                currentProcess = processQueue.poll();
            }

            if (currentProcess != null) {
                int tier = currentProcess.getTier();
                int remainingTime = currentProcess.getRemainingTime();

                remainingTime--;
                currentProcess.setRemainingTime(remainingTime);

                int brust = currentProcess.getBurstTime();
                int quantamUsed = brust - remainingTime;

                if (remainingTime == 0) {
                    currentProcess.setFinishTime(currentTime + 1);
                    completed++;
                    currentProcess = null;
                    csRemaining = CStime;
                } else if (remainingTime > 0 && quantamUsed == QuantamTime && tier == 1) {
                    demoteToTier2(currentProcess);
                    currentProcess = null;
                    csRemaining = CStime;
                }
            }
            // tier 2

            currentTime++;
        }
    }

    public void printHeuristicDemotionMatrix() {

    }

    public void applyAging() {

    }
}