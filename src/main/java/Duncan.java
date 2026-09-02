import java.time.LocalDate;

public class Duncan {
    /** Where the task list is kept between runs, relative to the project root. */
    private static final String DATA_FILE_PATH = "data/duncan.txt";

    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();

        // Pick up where the previous run left off; an empty list if there
        // is no save file yet.
        Storage storage = new Storage(DATA_FILE_PATH);
        TaskList tasks = new TaskList(storage.load());

        String input = ui.readCommand();
        while (!input.equals("bye")) {
            ui.printDivider();
            try {
                String commandWord = Parser.getCommandWord(input);
                String rest = Parser.getArguments(input);

                switch (commandWord) {
                case "list":
                    ui.showTaskList(tasks.getTasks());
                    break;
                case "mark": {
                    int taskIndex = Parser.parseTaskIndex(rest, tasks.size());
                    tasks.get(taskIndex).markAsDone();
                    storage.save(tasks.getTasks());
                    ui.showTaskMarked(tasks.get(taskIndex));
                    break;
                }
                case "unmark": {
                    int taskIndex = Parser.parseTaskIndex(rest, tasks.size());
                    tasks.get(taskIndex).markAsNotDone();
                    storage.save(tasks.getTasks());
                    ui.showTaskUnmarked(tasks.get(taskIndex));
                    break;
                }
                case "delete": {
                    int taskIndex = Parser.parseTaskIndex(rest, tasks.size());
                    Task removedTask = tasks.remove(taskIndex);
                    storage.save(tasks.getTasks());
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
                    storage.save(tasks.getTasks());
                    ui.showTaskAdded(newTask, tasks.size());
                    break;
                }
                case "deadline": {
                    String[] parts = Parser.splitDeadlineArgs(rest);
                    String description = parts[0].trim();
                    LocalDate by = Parser.parseDate(parts[1]);
                    if (description.isEmpty()) {
                        throw new DukeException("HEY! the description can't be left empty");
                    }
                    Task newTask = new Deadline(description, by);
                    tasks.add(newTask);
                    storage.save(tasks.getTasks());
                    ui.showTaskAdded(newTask, tasks.size());
                    break;
                }
                case "event": {
                    String[] parts = Parser.splitEventArgs(rest);
                    String description = parts[0];
                    LocalDate from = Parser.parseDate(parts[1]);
                    LocalDate to = Parser.parseDate(parts[2]);
                    if (description.isEmpty()) {
                        throw new DukeException("HEY! the description can't be left empty");
                    }
                    Task newTask = new Event(description, from, to);
                    tasks.add(newTask);
                    storage.save(tasks.getTasks());
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
