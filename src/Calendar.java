import java.time.LocalTime;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Calendar {
    private final ArrayList<ArrayList<Event>> events;

    public Calendar(int numberOfDays) {
        this.events = new ArrayList<ArrayList<Event>>();
        for (int i = 0; i <= numberOfDays; i++) {
            this.events.add(new ArrayList<Event>());
        }
    }

    public Calendar() {
        this(7);
    }

    public ArrayList<ArrayList<Event>> getEvents() {
        return events;
    }

    public void addEventOfTheDay(String description, LocalTime startTime, LocalTime endTime, Priority priority, int day) {
        Meeting event = new Meeting(description, startTime, endTime, priority);
        events.get(day).add(event);
    }

    public void addEventOfTheDay(String description, LocalTime startTime, LocalTime endTime, Status status, int day) {
        Task task = new Task(description, startTime, endTime, status);
        events.get(day).add(task);
    }

    public void removeEventOfTheDay(int index, int day) {
        events.get(day).remove(index);
    }

    public ArrayList<Event> getEventsOfTheDay(int day, Predicate<Event> condition) {
        ArrayList<Event> eventsOfTheDay = events.get(day);
        ArrayList<Event> eventsOfTheDayFiltered = new ArrayList<>();
        for (Event event : eventsOfTheDay) {
            if (condition.test(event)) {
                eventsOfTheDayFiltered.add(event);
            }
        }
        return eventsOfTheDayFiltered;
    }

    public ArrayList<Event> getEventsOfTheDayByPriority(int day, Priority priority) {
        Predicate<Event> condition = event -> {
            if (event instanceof Meeting meeting) {
                return meeting.getPriority() == priority;
            }
            return false;
        };
        return getEventsOfTheDay(day, condition);
    }

    public ArrayList<Event> getEventsOfTheDayByStatus(int day, Status status) {
        Predicate<Event> condition = event -> {
            if (event instanceof Task task) {
                return task.getStatus() == status;
            }
            return false;
        };
        return getEventsOfTheDay(day, condition);
    }

    public ArrayList<Event> getEventsOfTheDayByTime(int day, LocalTime time) {
        Predicate<Event> condition = event -> event.getStartTime().isAfter(time) || event.getStartTime().equals(time);
        return getEventsOfTheDay(day, condition);
    }

    public ArrayList<Event> getEventsOfTheDayBetweenTimes(int day, LocalTime startTime, LocalTime endTime) {
        Predicate<Event> condition = event -> (event.getStartTime().isAfter(startTime) || event.getStartTime().equals(startTime)) && (event.getEndTime().isBefore(endTime) || event.getEndTime().equals(endTime));
        return getEventsOfTheDay(day, condition);
    }

    public ArrayList<Event> getEventsOfTheDayByPriorityAndTime(int day, Priority priority, LocalTime time) {
        Predicate<Event> condition = event -> {
            if (event instanceof Meeting meeting) {
                return meeting.getPriority() == priority && (meeting.getStartTime().isAfter(time) || meeting.getStartTime().equals(time));
            }
            return false;
        };
        return getEventsOfTheDay(day, condition);
    }

    public ArrayList<Event> getEventsOfTheDayByStatusAndTime(int day, Status status, LocalTime time) {
        Predicate<Event> condition = event -> {
            if (event instanceof Task task) {
                return task.getStatus() == status && (task.getEndTime().isBefore(time) || task.getEndTime().equals(time));
            }
            return false;
        };
        return getEventsOfTheDay(day, condition);
    }
}
