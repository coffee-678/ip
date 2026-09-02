package duncan.task;

import java.util.ArrayList;

import duncan.Storage;
import duncan.Ui;

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

    /** Adds {@code task} to the end of the list. */
    public void add(Task task) {
        tasks.add(task);
    }

    /** Removes and returns the task at the given index. */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /** Returns the task at {@code index}, without removing it. */
    public Task get(int index) {
        return tasks.get(index);
    }

    /** Returns the number of tasks in the list. */
    public int size() {
        return tasks.size();
    }

    /** Whether {@code index} refers to an actual task in this list. */
    public boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    /** Returns the tasks whose description contains {@code keyword} (case-insensitive). */
    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matches.add(task);
            }
        }
        return matches;
    }

    /** Returns the underlying list, e.g. for {@link Storage} to save or {@link Ui} to display. */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
