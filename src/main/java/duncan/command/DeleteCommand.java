package duncan.command;

import duncan.DuncanException;
import duncan.Storage;
import duncan.Ui;
import duncan.task.Task;
import duncan.task.TaskList;

/** Removes the task at a given 0-based index from the task list. */
public class DeleteCommand extends Command {
    private final int taskIndex;

    /**
     * @param taskIndex the 0-based index of the task to remove
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuncanException {
        if (!tasks.isValidIndex(taskIndex)) {
            throw new DuncanException("HEY! this task number is bad");
        }
        Task removedTask = tasks.remove(taskIndex);
        storage.save(tasks.getTasks());
        ui.showTaskDeleted(removedTask, tasks.size());
    }
}
