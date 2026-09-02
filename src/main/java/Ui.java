import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all interaction with the user: everything the program prints to
 * the console, and reading the commands the user types in response.
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

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /** Prints the banner and greeting shown when the program starts. */
    public void showWelcome() {
        showLine();
        System.out.println(BANNER);
        System.out.println("Hello! I'm Duncan.");
        System.out.println("What can I do for you?");
        showLine();
        showBlankLine();
    }

    /**
     * Prints the farewell shown when the program exits. Like every other
     * command's result, this relies on the main loop to print the dividers
     * around it.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /** Prints the horizontal rule used to separate sections of output. */
    public void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    /** Prints a blank line, used to visually separate command responses. */
    public void showBlankLine() {
        System.out.println();
    }

    /** Reads one line of console input typed by the user. */
    public String readCommand() {
        return scanner.nextLine();
    }

    public void showTaskList(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /** Prints the message of a {@link DukeException} caught from a bad command. */
    public void showError(String message) {
        System.out.println(message);
    }
}
