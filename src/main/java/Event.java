import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that starts on a specific date and ends on a
 * specific date, e.g. "project fair from 2019-12-01 to 2019-12-02".
 * The dates are stored as {@link LocalDate}, so they are validated on
 * input and can be displayed in a friendlier format than typed in.
 */
public class Event extends Task {
    /** Format the dates are displayed in, e.g. "Dec 2 2019". */
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");

    protected LocalDate from;
    protected LocalDate to;

    public Event(String description, LocalDate from, LocalDate to) {
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
        return "[E]" + super.toString() + " (from: " + from.format(DISPLAY_FORMAT)
                + " to: " + to.format(DISPLAY_FORMAT) + ")";
    }
}
