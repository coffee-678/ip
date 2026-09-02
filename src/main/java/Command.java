/**
 * Represents one user command, already parsed and ready to run. Each kind
 * of command (adding a task, marking one done, exiting, etc.) is its own
 * subclass that knows how to carry out that one action.
 */
public abstract class Command {
    /**
     * Carries out this command: updates the task list as needed, reports
     * the result through {@code ui}, and persists any change through
     * {@code storage}.
     *
     * @throws DukeException if the command cannot be carried out, e.g. a
     *         task number that is out of range
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException;

    /** Whether this command should end the program's main loop. */
    public boolean isExit() {
        return false;
    }
}
