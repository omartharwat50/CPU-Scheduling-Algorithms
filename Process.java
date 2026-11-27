public class Process {
    private String name;
    private int waitingTime = 0, turnaroundTime = 0, responseTime = 0, arrivalTime = 0, burstTime = 0,
            remainingTime = 0, initialPriority = 0, effectivePriority = 0, finishTime = 0;
    private boolean isBatchJob = false;
    private int tier2WaitStartTime = 0, tier = 1;

    public Process(String name) {
        this.name = name;
        this.remainingTime = this.burstTime;
    }

    public String getName() {
        return name;
    }

    public int getInitialPriority() {
        return initialPriority;
    }

    public void setInitialPriority(int v) {
        initialPriority = v;
    }

    public int getEffectivePriority() {
        return effectivePriority;
    }

    public void setEffectivePriority(int v) {
        effectivePriority = v;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int v) {
        waitingTime = v;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int v) {
        turnaroundTime = v;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int v) {
        responseTime = v;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int v) {
        arrivalTime = v;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int v) {
        burstTime = v;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int v) {
        remainingTime = v;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int v) {
        finishTime = v;
    }

    public boolean isBatchJob() {
        return isBatchJob;
    }

    public void setBatchJob(boolean v) {
        isBatchJob = v;
    }

    public int getTier2WaitStartTime() {
        return tier2WaitStartTime;
    }

    public void setTier2WaitStartTime(int v) {
        tier2WaitStartTime = v;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int v) {
        tier = v;
    }
}
