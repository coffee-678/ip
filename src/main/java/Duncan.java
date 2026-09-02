import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Duncan {
    /** Where the task list is kept between runs, relative to the project root. */
    private static final String DATA_FILE_PATH = "data/duncan.txt";

    private static void printAddedTask(Task newTask, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + newTask);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    private static LocalDate parseDate(String rest) throws DukeException {
        try {
            return LocalDate.parse(rest.trim());
        } catch (DateTimeParseException e) {
            throw new DukeException("HEY! dates must be in yyyy-mm-dd format");
        }
    }

    private static int parseTaskIndex(String rest, int taskCount) throws DukeException {
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(rest.trim());
        } catch (NumberFormatException e) {
            throw new DukeException("HEY! this task number is bad");
        }
        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new DukeException("HEY! this task number is bad");
        }
        return taskNumber - 1;
    }

    public static void main(String[] args) {
        String banner = " /$$$$$$$                                                   \n"
                + "| $$__  $$                                                  \n"
                + "| $$  \\ $$ /$$   /$$ /$$$$$$$   /$$$$$$$  /$$$$$$  /$$$$$$$ \n"
                + "| $$  | $$| $$  | $$| $$__  $$ /$$_____/ |____  $$| $$__  $$\n"
                + "| $$  | $$| $$  | $$| $$  \\ $$| $$        /$$$$$$$| $$  \\ $$\n"
                + "| $$  | $$| $$  | $$| $$  | $$| $$       /$$__  $$| $$  | $$\n"
                + "| $$$$$$$/|  $$$$$$/| $$  | $$|  $$$$$$$|  $$$$$$$| $$  | $$\n"
                + "|_______/  \\______/ |__/  |__/ \\_______/ \\_______/|__/  |__/\n";
        String horizontalLine = "____________________________________________________________";

        System.out.println(horizontalLine);
        System.out.println(banner);
        System.out.println("Hello! I'm Duncan.");
        System.out.println("What can I do for you?");
        System.out.println(horizontalLine);
        System.out.println();

        // Pick up where the previous run left off; an empty list if there
        // is no save file yet.
        Storage storage = new Storage(DATA_FILE_PATH);
        ArrayList<Task> tasks = storage.load();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            System.out.println(horizontalLine);
            try {
                String[] inputParts = input.split(" ", 2);
                String commandWord = inputParts[0];
                String rest = inputParts.length > 1 ? inputParts[1] : "";

                switch (commandWord) {
                case "list":
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                    break;
                case "mark": {
                    int taskIndex = parseTaskIndex(rest, tasks.size());
                    tasks.get(taskIndex).markAsDone();
                    storage.save(tasks);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(taskIndex));
                    break;
                }
                case "unmark": {
                    int taskIndex = parseTaskIndex(rest, tasks.size());
                    tasks.get(taskIndex).markAsNotDone();
                    storage.save(tasks);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks.get(taskIndex));
                    break;
                }
                case "delete": {
                    int taskIndex = parseTaskIndex(rest, tasks.size());
                    Task removedTask = tasks.remove(taskIndex);
                    storage.save(tasks);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    break;
                }
                case "todo": {
                    String description = rest.trim();
                    if (description.isEmpty()) {
                        throw new DukeException("HEY! the description can't be left empty");
                    }
                    Task newTask = new Todo(description);
                    tasks.add(newTask);
                    storage.save(tasks);
                    printAddedTask(newTask, tasks.size());
                    break;
                }
                case "deadline": {
                    String[] parts = rest.split("/by ", 2);
                    if (parts.length < 2) {
                        throw new DukeException("HEY! deadlines must have /by <date/time>");
                    }
                    String description = parts[0].trim();
                    LocalDate by = parseDate(parts[1]);
                    if (description.isEmpty()) {
                        throw new DukeException("HEY! the description can't be left empty");
                    }
                    Task newTask = new Deadline(description, by);
                    tasks.add(newTask);
                    storage.save(tasks);
                    printAddedTask(newTask, tasks.size());
                    break;
                }
                case "event": {
                    int fromIndex = rest.indexOf("/from ");
                    int toIndex = rest.indexOf("/to ");
                    if (fromIndex == -1 || toIndex == -1) {
                        throw new DukeException("HEY! events must use /from and /to <date/time>");
                    }
                    String description = rest.substring(0, fromIndex).trim();
                    LocalDate from = parseDate(rest.substring(fromIndex + 6, toIndex));
                    LocalDate to = parseDate(rest.substring(toIndex + 4));
                    if (description.isEmpty()) {
                        throw new DukeException("HEY! the description can't be left empty");
                    }
                    Task newTask = new Event(description, from, to);
                    tasks.add(newTask);
                    storage.save(tasks);
                    printAddedTask(newTask, tasks.size());
                    break;
                }
                default:
                    throw new DukeException("HEY! idk what's that supposed to be");
                }
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(horizontalLine);
            System.out.println();
            input = scanner.nextLine();
        }

        System.out.println(horizontalLine);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);
    }
}
