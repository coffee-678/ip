package duncan.command;

import duncan.DuncanException;
import duncan.Storage;
import duncan.Ui;
import duncan.task.TaskList;

/** Marks the task at a given 0-based index as done. */
public class MarkCommand extends Command {
    private final int taskIndex;

    /**
     * Creates a command that marks the task at the given index as done.
     *
     * @param taskIndex The 0-based index of the task to mark as done.
     */
    public MarkCommand(int taskIndex) {
        assert taskIndex >= 0;
        this.taskIndex = taskIndex;
    }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuncanException {
        checkTaskIndex(tasks, taskIndex);
        tasks.get(taskIndex).markAsDone();
        storage.save(tasks.getTasks());
        ui.showTaskMarked(tasks.get(taskIndex));
    }
}
