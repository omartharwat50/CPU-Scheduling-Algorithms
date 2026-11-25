public class Process{
    private String name;
    private int waitingTime=0;
    private int turnaroundTime=0;
    private int responseTime=0;
    private int arrivalTime=0;
    private int burstTime=0;
    private int remainingTime=0;
    private int initialPriority=0;
    private int effectivePriority=0;
    private int finishTime=0;
    private boolean isBatchJob=false;
    private int tier2WaitStartTime=0;

    
    public Process(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public int getInitialPriority(){
        return initialPriority;
    }
    public void setInitialPriority(int initialPriority){
        this.initialPriority=initialPriority;
    }
    public int getEffectivePriority(){
        return effectivePriority;
    }
    public void setEffectivePriority(int effectivePriority){
        this.effectivePriority=effectivePriority;
    }
    public int getWaitingTime(){
        return waitingTime;
    }
    public void setWaitingTime(int waitingTime){
        this.waitingTime=waitingTime;
    }
    public int getTurnaroundTime(){
        return turnaroundTime;
    }
    public void setTurnaroundTime(int turnaroundTime){
        this.turnaroundTime=turnaroundTime;
    }
    public int getResponseTime(){
        return responseTime;
    }
    public void setResponseTime(int responseTime){
        this.responseTime=responseTime;
    }
    public int getArrivalTime(){
        return arrivalTime;
    }
    public void setArrivalTime(int arrivalTime){
        this.arrivalTime=arrivalTime;
    }
    public int getBurstTime(){
        return burstTime;
    }
    public void setBurstTime(int burstTime){
        this.burstTime=burstTime;
    }
    public int getRemainingTime(){
        return remainingTime;
    }
    public int getFinishTime(){
        return finishTime;
    }
    public void setFinishTime(int finishTime){
        this.finishTime=finishTime;
    }
    public boolean isBatchJob(){
        return isBatchJob;
    }
    public void setBatchJob(boolean isBatchJob){
        this.isBatchJob=isBatchJob;
    }

    

}