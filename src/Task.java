import java.time.LocalTime;

public non-sealed class Task extends Event {

    private Status status;

    public Task(String description, LocalTime startTime, LocalTime endTime, Status status) {
        super(description, startTime, endTime);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toStringHelper() {
        return String.format("Status: %s", getStatus());
    }
}
