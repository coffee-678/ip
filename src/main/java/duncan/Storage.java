package duncan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import duncan.task.Deadline;
import duncan.task.Event;
import duncan.task.Task;
import duncan.task.Todo;

/**
 * Loads the task list from a file on the hard disk when the program starts,
 * and writes it back whenever the list changes, so that tasks survive
 * between runs of the program.
 *
 * <p>Each task occupies one line of the file, with its fields separated by
 * tab characters. The first field is the task type ("T", "D" or "E"), the
 * second is the done status ("1" for done, "0" for not done), the third is
 * the description, and any further fields are that type's date details,
 * written in ISO-8601 form (yyyy-mm-dd) since that is both
 * {@link LocalDate}'s default text form and the format tasks are typed
 * in. Written out with the tabs shown as arrows, a file looks like:
 *
 * <pre>
 * T &#8594; 1 &#8594; borrow book
 * D &#8594; 0 &#8594; return book &#8594; 2019-12-02
 * E &#8594; 0 &#8594; project fair &#8594; 2019-12-01 &#8594; 2019-12-02
 * </pre>
 *
 * <p>A tab is used as the separator because it is not something a user can
 * type into a task description at the console, so it cannot be confused
 * with the text of a task.
 */
public class Storage {
    /** The file the task list is read from and written to. */
    private final File file;

    /**
     * @param filePath path to the save file, relative to the directory the
     *                 program is run from (e.g. "data/duncan.txt")
     */
    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * Reads the saved task list from the hard disk.
     *
     * @return the saved tasks, or an empty list if there is no save file
     *         yet (i.e. on the very first run, or if the data folder was
     *         never created)
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!file.exists()) {
            return tasks;
        }
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (!line.isEmpty()) {
                    tasks.add(parseTask(line));
                }
            }
        } catch (FileNotFoundException e) {
            // The file existed a moment ago but cannot be opened now, so
            // there is nothing to restore; carry on with an empty list.
            System.out.println("Couldn't read " + file + "; starting with an empty task list.");
        }
        return tasks;
    }

    /**
     * Writes the whole task list to the hard disk, replacing whatever was
     * saved before. The containing folder (e.g. "data/") is created if it
     * does not exist yet.
     *
     * @param tasks the current task list
     */
    public void save(ArrayList<Task> tasks) {
        File folder = file.getParentFile();
        if (folder != null) {
            folder.mkdirs();
        }
        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Couldn't save to " + file + "; your tasks may be lost when you exit.");
        }
    }

    /**
     * Rebuilds a single task from the line that {@link Task#toFileFormat()}
     * wrote for it.
     */
    private static Task parseTask(String line) {
        String[] fields = line.split(Task.FIELD_SEPARATOR);
        String type = fields[0];
        boolean isDone = fields[1].equals("1");
        String description = fields[2];

        Task task;
        switch (type) {
        case "D":
            task = new Deadline(description, LocalDate.parse(fields[3]));
            break;
        case "E":
            task = new Event(description, LocalDate.parse(fields[3]), LocalDate.parse(fields[4]));
            break;
        default: // "T"
            task = new Todo(description);
            break;
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
