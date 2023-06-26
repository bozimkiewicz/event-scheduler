import java.time.LocalTime;

sealed abstract class Event permits Meeting, Task {
    public final static LocalTime MIN_TIME = LocalTime.of(8, 0);
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;

    protected Event(String description, LocalTime startTime, LocalTime endTime) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public abstract String toStringHelper();

    @Override
    public String toString() {
        return String.format("Description: %s\nTime: %s - %s\n%s", getDescription(), getStartTime(), getEndTime(), toStringHelper());
    }
}
