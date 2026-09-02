package duncan.command;

import duncan.DuncanException;
import duncan.Storage;
import duncan.Ui;
import duncan.task.Task;
import duncan.task.TaskList;

/**
 * Adds one already-built task (a {@link duncan.task.Todo}, {@link duncan.task.Deadline}, or
 * {@link duncan.task.Event}) to the task list.
 */
public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuncanException {
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(task, tasks.size());
    }
}
