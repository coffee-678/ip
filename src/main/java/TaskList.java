import java.util.ArrayList;

/**
 * Holds the list of tasks and the operations that can be performed on it:
 * adding, removing, retrieving, and counting tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list wrapping an already-populated list, e.g. one just
     * read from the save file by {@link Storage}.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    /** Removes and returns the task at the given index. */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    /** Whether {@code index} refers to an actual task in this list. */
    public boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    /** Returns the underlying list, e.g. for {@link Storage} to save or {@link Ui} to display. */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
