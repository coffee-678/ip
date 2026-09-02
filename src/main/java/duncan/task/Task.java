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

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Separates the fields within one task's line in the save file. */
    public static final String FIELD_SEPARATOR = "\t";

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getDescription() {
        return description;
    }

    public void markAsDone() {
        isDone = true;
    }

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

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
