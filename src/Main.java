import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program can be used to manage events in a calendar.");
        System.out.println("Enter number of days in the calendar:");
        Calendar calendar = createCalendar();
        displayOptions();
        boolean isRunning = true;
        while (isRunning) {
            Scanner readerEvent = new Scanner(System.in);
            int choiceEvent = readerEvent.nextInt();
            switch (choiceEvent) {
                case 1 -> addMeeting(calendar);
                case 2 -> addTask(calendar);
                case 3 -> removeEvent(calendar);
                case 4 -> displayMeetingsOfTheDay(calendar);
                case 5 -> displayTasksOfTheDay(calendar);
                case 6 -> displayMeetingsOfTheDayByPriority(calendar);
                case 7 -> displayTasksOfTheDayByStatus(calendar);
                case 8 -> displayMeetingsOfTheDayByPriorityAndTime(calendar);
                case 9 -> displayTasksOfTheDayByStatusAndTime(calendar);
                case 0 -> isRunning = false;
                default -> System.out.println("Wrong choice!");
            }
        }
    }

    public static void displayOptions() {
        System.out.println("""
                Choose what do you want to do:
                1 - add meeting
                2 - add task
                3 - remove event
                4 - display meetings of the day
                5 - display tasks of the day
                6 - display meetings of the day by priority
                7 - display tasks of the day by status
                8 - display meetings of the day by priority and time
                9 - display tasks of the day by status and time
                0 - exit""");
    }

    public static Calendar createCalendar() {
        Scanner reader = new Scanner(System.in);
        int numberOfDays = reader.nextInt();
        if (numberOfDays != 0) {
            return new Calendar(numberOfDays);
        } else {
            return new Calendar();
        }
    }

    public static void addMeeting(Calendar calendar) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter description of the meeting:");
        String description = reader.nextLine();
        System.out.println("Enter start time of the meeting:");
        LocalTime startTime = LocalTime.parse(reader.nextLine());
        while (startTime.isBefore(Event.MIN_TIME)) {
            System.out.println("Start time cannot be earlier than 8:00!");
            System.out.println("Enter start time of the meeting:");
            startTime = LocalTime.parse(reader.nextLine());
        }
        System.out.println("Enter end time of the meeting:");
        LocalTime endTime = LocalTime.parse(reader.nextLine());
        Priority priority = choosePriority();
        int dayOfMeeting = getDay(calendar);
        calendar.addEventOfTheDay(description, startTime, endTime, priority, dayOfMeeting);
        System.out.println("Meeting added!\nWhat do you want to do?");
    }

    public static void removeEvent(Calendar calendar) {
        if (calendar.getEvents().isEmpty()) {
            System.out.println("There are no events to remove!");
            System.out.println("What do you want to do?");
            return;
        }
        int dayOfEvent = getDay(calendar);
        System.out.println("Enter index of the event:");
        Scanner reader = new Scanner(System.in);
        int index = reader.nextInt();
        calendar.removeEventOfTheDay(index, dayOfEvent);
        System.out.println("Event removed!\nWhat do you want to do?");
    }

    public static void displayMeetingsOfTheDay(Calendar calendar) {
        int dayOfMeeting = getDay(calendar);
        System.out.printf("Date: %s\n", getDateName(dayOfMeeting));
        int meetingCounter = 1;
        for (Event meeting : calendar.getEvents().get(dayOfMeeting)) {
            if (meeting instanceof Meeting) {
                System.out.printf("Meeting #%d:\n", meetingCounter);
                System.out.println(meeting);
                meetingCounter++;
            }
        }
        System.out.println("What do you want to do?");
    }

    public static void displayMeetingsOfTheDayByPriority(Calendar calendar) {
        int dayOfEvent = getDay(calendar);
        Priority priority = choosePriority();
        int eventCounter = 1;
        System.out.printf("Date: %s\n", getDateName(dayOfEvent));
        for (Event event : calendar.getEventsOfTheDayByPriority(dayOfEvent, priority)) {
            if (event instanceof Meeting) {
                System.out.printf("Meeting #%d:\n", eventCounter);
                System.out.println(event);
                eventCounter++;
            }
        }
        System.out.println("What do you want to do?");
    }

    public static void displayMeetingsOfTheDayByPriorityAndTime(Calendar calendar) {
        int dayOfMeeting = getDay(calendar);
        Priority priority = choosePriority();
        System.out.println("Enter from what time you want to see the meetings:");
        Scanner reader = new Scanner(System.in);
        LocalTime startTime = LocalTime.parse(reader.nextLine());
        int meetingCounter = 1;
        System.out.printf("Date: %s\n", getDateName(dayOfMeeting));
        for (Event event : calendar.getEventsOfTheDayByPriorityAndTime(dayOfMeeting, priority, startTime)) {
            System.out.printf("Meeting #%d:\n", meetingCounter);
            System.out.println(event);
            meetingCounter++;
        }
        System.out.println("What do you want to do?");
    }

    public static void addTask(Calendar calendar) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter description of the task:");
        String description = reader.nextLine();
        System.out.println("Enter start time of the task:");
        LocalTime startTime = LocalTime.parse(reader.nextLine());
        while (startTime.isBefore(Event.MIN_TIME)) {
            System.out.println("Start time cannot be earlier than 8:00!");
            System.out.println("Enter start time of the task:");
            startTime = LocalTime.parse(reader.nextLine());
        }
        System.out.println("Enter end time of the task:");
        LocalTime endTime = LocalTime.parse(reader.nextLine());
        Status status = chooseStatus();
        int dayOfTask = getDay(calendar);
        calendar.addEventOfTheDay(description, startTime, endTime, status, dayOfTask);
        System.out.println("Task added!\nWhat do you want to do?");
    }

    public static void displayTasksOfTheDay(Calendar calendar) {
        int dayOfTask = getDay(calendar);
        System.out.printf("Date: %s\n", getDateName(dayOfTask));
        int taskCounter = 1;
        for (Event task : calendar.getEvents().get(dayOfTask)) {
            if (task instanceof Task) {
                System.out.printf("Task #%d:\n", taskCounter);
                System.out.println(task);
                taskCounter++;
            }
        }
        System.out.println("What do you want to do?");
    }

    public static void displayTasksOfTheDayByStatus(Calendar calendar) {
        int dayOfTask = getDay(calendar);
        Status status = chooseStatus();
        System.out.printf("Date: %s\n", getDateName(dayOfTask));
        int taskCounter = 1;
        for (Event task : calendar.getEventsOfTheDayByStatus(dayOfTask, status)) {
            if (task instanceof Task) {
                System.out.printf("Task #%d:\n", taskCounter);
                System.out.println(task);
                taskCounter++;
            }
        }
        System.out.println("What do you want to do?");
    }

    public static void displayTasksOfTheDayByStatusAndTime(Calendar calendar) {
        int dayOfTask = getDay(calendar);
        Status status = chooseStatus();
        System.out.println("Enter to what time you want to see the tasks:");
        Scanner reader = new Scanner(System.in);
        LocalTime endTime = LocalTime.parse(reader.nextLine());
        System.out.printf("Date: %s\n", getDateName(dayOfTask));
        int taskCounter = 1;
        for (Event task : calendar.getEventsOfTheDayByStatusAndTime(dayOfTask, status, endTime)) {
            if (task instanceof Task) {
                System.out.printf("Task #%d:\n", taskCounter);
                System.out.println(task);
                taskCounter++;
            }
        }
        System.out.println("What do you want to do?");
    }

    public static LocalDate getDateName(int days) {
        return LocalDate.now().plusDays(days);
    }

    public static int getDay(Calendar calendar) {
        System.out.println("Enter day of the event:");
        Scanner reader = new Scanner(System.in);
        int dayOfMeeting = reader.nextInt();
        while (dayOfMeeting < 0 || dayOfMeeting >= calendar.getEvents().size()) {
            System.out.printf("Day of the event must be between 0 and %d!\n", calendar.getEvents().size() - 1);
            System.out.println("Enter day of the event:");
            dayOfMeeting = reader.nextInt();
        }
        return dayOfMeeting;
    }

    public static Priority choosePriority() {
        System.out.println("Enter priority of the meeting:");
        Scanner reader = new Scanner(System.in);
        int priorityNum = reader.nextInt();
        while (priorityNum < 1 || priorityNum > 3) {
            System.out.println("Priority must be between 1 and 3!");
            System.out.println("Enter priority of the meeting:");
            priorityNum = reader.nextInt();
        }
        return Priority.values()[priorityNum - 1];
    }

    public static Status chooseStatus() {
        System.out.println("Enter status of the task:");
        Scanner reader = new Scanner(System.in);
        int statusNum = reader.nextInt();
        while (statusNum < 1 || statusNum > 4) {
            System.out.println("Status must be between 1 and 4!");
            System.out.println("Enter status of the task:");
            statusNum = reader.nextInt();
        }
        return Status.values()[statusNum - 1];
    }
}