package duncan;

import duncan.command.Command;
import duncan.task.TaskList;

/**
 * A simple task manager. Carries out the commands the user gives it and
 * saves the task list to disk after every change, so it survives between
 * runs.
 *
 * <p>The same instance backs both front ends: {@link #run()} drives the
 * console app, while a GUI feeds it one line at a time through
 * {@link #getResponse(String)}.
 */
public class Duncan {
    /** Where the task list is kept between runs, relative to the project root. */
    private static final String DATA_FILE_PATH = "data/duncan.txt";

    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /** Whether the last command handled was the one that ends the session. */
    private boolean isExit = false;

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

    /** Sets up a Duncan wired to the default save file. */
    public Duncan() {
        this(DATA_FILE_PATH);
    }

    /**
     * Returns the greeting to show at the start of a session, without the
     * console app's banner and dividers.
     */
    public String getWelcome() {
        ui.showGreeting();
        return ui.flushOutput();
    }

    /**
     * Carries out one line of user input and returns everything Duncan has
     * to say in reply, including the message of any error the command ran
     * into. This is the whole of Duncan's behaviour as far as a GUI is
     * concerned.
     *
     * @param input one line as typed by the user, e.g. {@code "todo read book"}
     * @return Duncan's reply, as one or more lines of text.
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            c.execute(tasks, ui, storage);
            isExit = c.isExit();
        } catch (DuncanException e) {
            ui.showError(e.getMessage());
        }
        return ui.flushOutput();
    }

    /** Returns whether the last command handled asked to end the session. */
    public boolean isExit() {
        return isExit;
    }

    /** Greets the user, then reads and carries out commands until told to exit. */
    public void run() {
        ui.showWelcome();
        flushToConsole();
        while (!isExit) {
            String fullCommand = ui.readCommand();

            ui.showLine();
            flushToConsole();

            System.out.print(getResponse(fullCommand));

            ui.showLine();
            ui.showBlankLine();
            flushToConsole();
        }
    }

    /** Prints whatever the {@link Ui} has collected so far to the console. */
    private void flushToConsole() {
        System.out.print(ui.flushOutput());
    }

    /** Program entry point for the console app. */
    public static void main(String[] args) {
        new Duncan().run();
    }
}
