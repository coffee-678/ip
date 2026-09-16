package duncan;

import java.util.ArrayList;
import java.util.Scanner;

import duncan.task.Task;

/**
 * Handles all interaction with the user: everything the program has to say
 * to the user, and reading the commands the user types in response.
 *
 * <p>Messages are collected into a buffer rather than printed straight
 * away, so that the same {@code show...} methods can serve both front ends:
 * the console app prints the buffer, while the GUI puts it in a dialog box.
 */
public class Ui {
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________";
    private static final String BANNER =
            " /$$$$$$$                                                   \n"
            + "| $$__  $$                                                  \n"
            + "| $$  \\ $$ /$$   /$$ /$$$$$$$   /$$$$$$$  /$$$$$$  /$$$$$$$ \n"
            + "| $$  | $$| $$  | $$| $$__  $$ /$$_____/ |____  $$| $$__  $$\n"
            + "| $$  | $$| $$  | $$| $$  \\ $$| $$        /$$$$$$$| $$  \\ $$\n"
            + "| $$  | $$| $$  | $$| $$  | $$| $$       /$$__  $$| $$  | $$\n"
            + "| $$$$$$$/|  $$$$$$/| $$  | $$|  $$$$$$$|  $$$$$$$| $$  | $$\n"
            + "|_______/  \\______/ |__/  |__/ \\_______/ \\_______/|__/  |__/\n";

    private final Scanner scanner;

    /** Collects the messages shown since the last {@link #flushOutput()} call. */
    private final StringBuilder outputBuffer;

    /** Creates a Ui that reads console input from standard input. */
    public Ui() {
        this.scanner = new Scanner(System.in);
        this.outputBuffer = new StringBuilder();
    }

    /**
     * Adds lines of text to the buffer of messages waiting to be shown.
     * Every {@code show...} method goes through here, so no part of the Ui
     * writes to the console directly, and a message made of several lines
     * is added as one call rather than one call per line.
     *
     * @param lines the lines to add, in the order they should be shown
     */
    private void showLines(String... lines) {
        for (String line : lines) {
            outputBuffer.append(line).append(System.lineSeparator());
        }
    }

    /**
     * Returns everything shown since this method was last called, and empties
     * the buffer so the next batch of messages starts clean.
     *
     * @return The collected messages, or an empty string if there were none.
     */
    public String flushOutput() {
        String output = outputBuffer.toString();
        outputBuffer.setLength(0);
        return output;
    }

    /** Shows the banner and greeting used when the console app starts. */
    public void showWelcome() {
        showLine();
        showLines(BANNER);
        showGreeting();
        showLine();
        showBlankLine();
    }

    /**
     * Shows just the greeting, without the banner or the dividers around it.
     * The GUI uses this, since the banner is ASCII art that only lines up in
     * a fixed-width console font.
     */
    public void showGreeting() {
        showLines("Hello! I'm Duncan.",
                "What can I do for you?");
    }

    /**
     * Shows the farewell used when the program exits. Like every other
     * command's result, this relies on the caller to add the dividers
     * around it.
     */
    public void showGoodbye() {
        showLines("Bye. Hope to see you again soon!");
    }

    /** Shows the horizontal rule used to separate sections of output. */
    public void showLine() {
        showLines(HORIZONTAL_LINE);
    }

    /** Shows a blank line, used to visually separate command responses. */
    public void showBlankLine() {
        showLines("");
    }

    /** Reads one line of console input typed by the user. */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Shows the current task list, numbered from 1 in the order the tasks
     * were added.
     */
    public void showTaskList(ArrayList<Task> tasks) {
        showLines("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            showLines((i + 1) + "." + tasks.get(i));
        }
    }

    /** Shows the tasks matching a find command's keyword, numbered from 1. */
    public void showMatchingTasks(ArrayList<Task> matches) {
        showLines("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            showLines((i + 1) + "." + matches.get(i));
        }
    }

    /** Reports that {@code task} was added, and how many tasks are in the list now. */
    public void showTaskAdded(Task task, int taskCount) {
        showLines("Got it. I've added this task:",
                "  " + task,
                "Now you have " + taskCount + " tasks in the list.");
    }

    /** Reports that {@code task} was marked as done. */
    public void showTaskMarked(Task task) {
        showLines("Nice! I've marked this task as done:",
                "  " + task);
    }

    /** Reports that {@code task} was marked as not done. */
    public void showTaskUnmarked(Task task) {
        showLines("OK, I've marked this task as not done yet:",
                "  " + task);
    }

    /**
     * Reports that a task was moved to new dates, showing it before and after.
     *
     * @param oldTask The task as it was displayed before rescheduling.
     * @param newTask The task after rescheduling.
     */
    public void showTaskRescheduled(String oldTask, Task newTask) {
        showLines("OK, I've rescheduled this task:",
                "  from: " + oldTask,
                "  to:   " + newTask);
    }

    /**
     * Reports that a task's dates were pushed back, showing it before and after.
     *
     * @param oldTask The task as it was displayed before snoozing.
     * @param newTask The task after snoozing.
     * @param days How many days the task's dates were pushed back by.
     */
    public void showTaskSnoozed(String oldTask, Task newTask, int days) {
        showLines("OK, I've snoozed this task by " + days + " days:",
                "  from: " + oldTask,
                "  to:   " + newTask);
    }

    /** Reports that {@code task} was removed, and how many tasks are left. */
    public void showTaskDeleted(Task task, int taskCount) {
        showLines("Noted. I've removed this task:",
                "  " + task,
                "Now you have " + taskCount + " tasks in the list.");
    }

    /** Shows the message of a {@link DuncanException} caught from a bad command. */
    public void showError(String message) {
        showLines(message);
    }
}
