/** Removes the task at a given 0-based index from the task list. */
public class DeleteCommand extends Command {
    private final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (!tasks.isValidIndex(taskIndex)) {
            throw new DukeException("HEY! this task number is bad");
        }
        Task removedTask = tasks.remove(taskIndex);
        storage.save(tasks.getTasks());
        ui.showTaskDeleted(removedTask, tasks.size());
    }
}
