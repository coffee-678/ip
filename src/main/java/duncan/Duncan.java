package duncan;

import duncan.command.Command;
import duncan.task.TaskList;

/**
 * A simple command-line task manager. Reads commands typed by the user in
 * a loop, carries each one out, and saves the task list to disk after
 * every change so it survives between runs.
 */
public class Duncan {
    /** Where the task list is kept between runs, relative to the project root. */
    private static final String DATA_FILE_PATH = "data/duncan.txt";

    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Sets up a Duncan wired to the given save file: creates its {@link Ui},
     * a {@link Storage} pointing at {@code filePath}, and loads the task
     * list saved there (or an empty list, if there is none yet).
     *
     * @param filePath path to the save file, relative to the directory the
     *                 program is run from
     */
    public Duncan(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = new TaskList(storage.load());
    }

    /** Greets the user, then reads and carries out commands until told to exit. */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DuncanException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
                ui.showBlankLine();
            }
        }
    }

    /** Program entry point: starts Duncan against the default save file. */
    public static void main(String[] args) {
        new Duncan(DATA_FILE_PATH).run();
    }
}
