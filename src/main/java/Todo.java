/**
 * Represents a task with no date/time attached, e.g. "borrow book".
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
