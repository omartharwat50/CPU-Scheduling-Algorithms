public class TimelineEntry {
    private final int startTime, endTime;
    private final String label;

    public TimelineEntry(int s, int e, String l) {
        startTime = s;
        endTime = e;
        label = l;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public String getLabel() {
        return label;
    }

    public String toString() {
        return "[" + startTime + " - " + endTime + "] " + label;
    }
}
