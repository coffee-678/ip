package duncan.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import duncan.DuncanException;
import duncan.Storage;
import duncan.Ui;
import duncan.task.Task;
import duncan.task.TaskList;
import duncan.task.Todo;

public class AddCommandTest {
    @TempDir
    Path tempDir;

    @Test
    public void execute_addsGivenTask_taskAppearsInList() throws DuncanException {
        TaskList tasks = new TaskList();
        Task task = new Todo("read book");
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());

        new AddCommand(task).execute(tasks, new Ui(), storage);

        assertEquals(1, tasks.size());
        assertSame(task, tasks.get(0));
    }
}
