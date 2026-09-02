package duncan.command;

import duncan.DukeException;
import duncan.Storage;
import duncan.Ui;
import duncan.task.TaskList;

/** Shows every task currently in the task list. */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        ui.showTaskList(tasks.getTasks());
    }
}
