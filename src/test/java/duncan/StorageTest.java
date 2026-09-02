package duncan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import duncan.task.Deadline;
import duncan.task.Event;
import duncan.task.Task;
import duncan.task.Todo;

public class StorageTest {
    @TempDir
    Path tempDir;

    @Test
    public void load_noSaveFileYet_emptyListReturned() {
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());

        assertTrue(storage.load().isEmpty());
    }

    @Test
    public void saveThenLoad_mixOfTaskTypesAndDoneStatuses_roundTripsCorrectly() {
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());

        Todo todo = new Todo("read book");
        Deadline deadline = new Deadline("return book", LocalDate.of(2019, 12, 2));
        deadline.markAsDone();
        Event event = new Event("project fair", LocalDate.of(2019, 12, 1), LocalDate.of(2019, 12, 2));

        ArrayList<Task> original = new ArrayList<>();
        original.add(todo);
        original.add(deadline);
        original.add(event);
        storage.save(original);

        ArrayList<Task> loaded = storage.load();

        assertEquals(3, loaded.size());
        assertEquals(todo.toString(), loaded.get(0).toString());
        assertEquals(deadline.toString(), loaded.get(1).toString());
        assertEquals(event.toString(), loaded.get(2).toString());
    }

    @Test
    public void save_parentFolderDoesNotExistYet_folderCreatedAndFileSaved() {
        Path savePath = tempDir.resolve("nested/dir/tasks.txt");
        Storage storage = new Storage(savePath.toString());
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("read book"));

        storage.save(tasks);

        assertTrue(Files.exists(savePath));
    }
}
