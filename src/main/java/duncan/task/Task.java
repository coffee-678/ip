package duncan.task;

import duncan.Storage;

/**
 * Represents a task with a description and a done/not-done status.
 * Concrete task types (e.g. {@link Todo}, {@link Deadline}, {@link Event})
 * extend this class to add their own scheduling details.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * @param description what the task is, as typed by the user
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Separates the fields within one task's line in the save file. */
    public static final String FIELD_SEPARATOR = "\t";

    /** Returns "X" if this task is done, or a single space if it is not. */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /** Returns this task's description, as typed by the user. */
    public String getDescription() {
        return description;
    }

    /** Marks this task as done. */
    public void markAsDone() {
        isDone = true;
    }

    /** Marks this task as not done. */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns this task as one line of text for the save file, in the
     * format described by {@link Storage}. Each subclass prefixes its own
     * type letter and appends its own date/time fields.
     */
    public abstract String toFileFormat();

    /**
     * Returns the fields every task shares in the save file: the done
     * status followed by the description.
     */
    protected String getSharedFileFields() {
        return (isDone ? "1" : "0") + FIELD_SEPARATOR + description;
    }

    /** Returns this task's status icon and description, e.g. "[X] read book". */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
