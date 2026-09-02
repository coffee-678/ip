/**
 * Represents a task that starts at a specific date/time and ends at a
 * specific date/time, e.g. "project meeting from Mon 2pm to 4pm". The
 * date/times are stored as-is, as Strings, without any parsing or
 * validation.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toFileFormat() {
        return "E" + FIELD_SEPARATOR + getSharedFileFields()
                + FIELD_SEPARATOR + from + FIELD_SEPARATOR + to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
