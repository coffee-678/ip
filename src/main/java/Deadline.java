/**
 * Represents a task that needs to be done before a specific date/time,
 * e.g. "return book by Sunday". The date/time is stored as-is, as a
 * String, without any parsing or validation.
 */
public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
