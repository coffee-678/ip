/** Marks the task at a given 0-based index as not done. */
public class UnmarkCommand extends Command {
    private final int taskIndex;

    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (!tasks.isValidIndex(taskIndex)) {
            throw new DukeException("HEY! this task number is bad");
        }
        tasks.get(taskIndex).markAsNotDone();
        storage.save(tasks.getTasks());
        ui.showTaskUnmarked(tasks.get(taskIndex));
    }
}
