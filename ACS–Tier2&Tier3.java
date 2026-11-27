import java.util.*;
private PriorityQueue<Process> tier2Queue =
        new PriorityQueue<>(
            Comparator.comparingInt(Process::getEffectivePriority)
                .thenComparingInt(Process::getTier2WaitStartTime)
                .thenComparing(Process::getName)
        );
private static final int AGING_INTERVAL = 4;
public void demoteToTier2(Process p, int currentTime) {
    if (p == null || tier2Queue.contains(p)) {
        return;
    }
    p.setTier2WaitStartTime(currentTime);
    p.setEffectivePriority(p.getInitialPriority());
    tier2Queue.offer(p);
}
public void applyAging(int currentTime) {
    for (Process p : tier2Queue) {
        int waitingTime =
                currentTime - p.getTier2WaitStartTime();
        int agingSteps =
                waitingTime / AGING_INTERVAL;
        int newPriority =
                p.getInitialPriority() - agingSteps;
        newPriority = Math.max(1, newPriority);
        p.setEffectivePriority(newPriority);
    }
    rebuildTier2Queue();
}
private void rebuildTier2Queue() {
    List<Process> temp =
            new ArrayList<>(tier2Queue);
    tier2Queue.clear();
    tier2Queue.addAll(temp);
}
public Process selectNextTier2Process(int currentTime) {
    applyAging(currentTime);
    if (tier2Queue.isEmpty()) {
        return null;
    }
    return tier2Queue.poll();
}
public void outputHeuristicDemotionMatrix(int timeStep) {
    applyAging(timeStep);
    System.out.println("\nTime: " + timeStep);
    System.out.print("Tier 1: [");
    StringJoiner tier1Output = new StringJoiner(", ");
    for (Process p : tier1Queue) {
        tier1Output.add(
            p.getName() +
            " (remaining: " + p.getRemainingTime() +
            ", Priority: " + p.getInitialPriority() + ")"
        );
    }
    System.out.print(
        tier1Queue.isEmpty() ? "Empty" : tier1Output
    );
    System.out.println("]");
    System.out.print("Tier 2: [");
    StringJoiner tier2Output = new StringJoiner(", ");
    List<Process> sortedProcesses =
            new ArrayList<>(tier2Queue);
    sortedProcesses.sort(
        Comparator.comparingInt(Process::getEffectivePriority)
            .thenComparingInt(Process::getTier2WaitStartTime)
            .thenComparing(Process::getName)
    );
    for (Process p : sortedProcesses) {
        tier2Output.add(
            p.getName() +
            " (remaining: " + p.getRemainingTime() +
            ", Priority: " + p.getEffectivePriority() + ")"
        );
    }
    System.out.print(
        tier2Queue.isEmpty() ? "Empty" : tier2Output
    );
    System.out.println("]");
}
