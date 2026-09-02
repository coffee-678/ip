import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Duncan {
    /** Where the task list is kept between runs, relative to the project root. */
    private static final String DATA_FILE_PATH = "data/duncan.txt";

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
        Ui ui = new Ui();
        ui.showWelcome();

        // Pick up where the previous run left off; an empty list if there
        // is no save file yet.
        Storage storage = new Storage(DATA_FILE_PATH);
        ArrayList<Task> tasks = storage.load();

        String input = ui.readCommand();
        while (!input.equals("bye")) {
            ui.printDivider();
            try {
                String[] inputParts = input.split(" ", 2);
                String commandWord = inputParts[0];
                String rest = inputParts.length > 1 ? inputParts[1] : "";

                switch (commandWord) {
                case "list":
                    ui.showTaskList(tasks);
                    break;
                case "mark": {
                    int taskIndex = parseTaskIndex(rest, tasks.size());
                    tasks.get(taskIndex).markAsDone();
                    storage.save(tasks);
                    ui.showTaskMarked(tasks.get(taskIndex));
                    break;
                }
                case "unmark": {
                    int taskIndex = parseTaskIndex(rest, tasks.size());
                    tasks.get(taskIndex).markAsNotDone();
                    storage.save(tasks);
                    ui.showTaskUnmarked(tasks.get(taskIndex));
                    break;
                }
                case "delete": {
                    int taskIndex = parseTaskIndex(rest, tasks.size());
                    Task removedTask = tasks.remove(taskIndex);
                    storage.save(tasks);
                    ui.showTaskDeleted(removedTask, tasks.size());
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
                    ui.showTaskAdded(newTask, tasks.size());
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
                    ui.showTaskAdded(newTask, tasks.size());
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
                    ui.showTaskAdded(newTask, tasks.size());
                    break;
                }
                default:
                    throw new DukeException("HEY! idk what's that supposed to be");
                }
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
            ui.printDivider();
            ui.printBlankLine();
            input = ui.readCommand();
        }

        ui.showGoodbye();
    }
}
