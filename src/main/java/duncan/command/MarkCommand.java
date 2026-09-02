package duncan.command;

import duncan.DuncanException;
import duncan.Storage;
import duncan.Ui;
import duncan.task.TaskList;

/** Marks the task at a given 0-based index as done. */
public class MarkCommand extends Command {
    private final int taskIndex;

    /**
     * @param taskIndex the 0-based index of the task to mark as done
     */
    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuncanException {
        if (!tasks.isValidIndex(taskIndex)) {
            throw new DuncanException("HEY! this task number is bad");
        }
        tasks.get(taskIndex).markAsDone();
        storage.save(tasks.getTasks());
        ui.showTaskMarked(tasks.get(taskIndex));
    }
}
