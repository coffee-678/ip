package duncan.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that needs to be done before a specific date,
 * e.g. "return book by 2019-12-02". The date is stored as a
 * {@link LocalDate}, so it is validated on input and can be displayed
 * in a friendlier format than it was typed in.
 */
public class Deadline extends Task {
    /** Format the date is displayed in, e.g. "Dec 2 2019". */
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");

    protected LocalDate by;

    /**
     * @param description what the task is, as typed by the user
     * @param by          the date this task is due
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /** Returns this deadline as a file line, e.g. "D\t0\treturn book\t2019-12-02". */
    @Override
    public String toFileFormat() {
        return "D" + FIELD_SEPARATOR + getSharedFileFields() + FIELD_SEPARATOR + by;
    }

    /** Returns this deadline for display, e.g. "[D][ ] return book (by: Dec 2 2019)". */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DISPLAY_FORMAT) + ")";
    }
}
