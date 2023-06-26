import java.time.LocalTime;

public final class Meeting extends Event {

    private Priority priority;

    public Meeting(String description, LocalTime startTime, LocalTime endTime, Priority priority) {
        super(description, startTime, endTime);
        this.priority = priority;
    }

    public Priority getPriority() {
        return priority;
    }

    @Override
    public String toStringHelper() {
        return String.format("Priority: %s", getPriority());
    }
}

