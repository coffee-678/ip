import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Makes sense of the raw text the user types: splitting a line into a
 * command word and its arguments, and picking apart each command's
 * arguments into the pieces (dates, task numbers, descriptions) that
 * {@link Duncan} needs to act on it.
 */
public class Parser {
    /** Returns the first word of the input line, e.g. "todo" from "todo read book". */
    public static String getCommandWord(String input) {
        return input.split(" ", 2)[0];
    }

    /** Returns everything after the first word, or "" if there is nothing else. */
    public static String getArguments(String input) {
        String[] parts = input.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }

    public static LocalDate parseDate(String rest) throws DukeException {
        try {
            return LocalDate.parse(rest.trim());
        } catch (DateTimeParseException e) {
            throw new DukeException("HEY! dates must be in yyyy-mm-dd format");
        }
    }

    /** Converts a 1-based task number typed by the user into a 0-based list index. */
    public static int parseTaskIndex(String rest, int taskCount) throws DukeException {
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(rest.trim());
        } catch (NumberFormatException e) {
            throw new DukeException("HEY! this task number is bad");
        }
        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new DukeException("HEY! this task number is bad");
        }
        return taskNumber - 1;
    }

    /**
     * Splits a "deadline" command's arguments on the "/by " marker.
     *
     * @return a two-element array: the untrimmed description text, and the raw date text
     */
    public static String[] splitDeadlineArgs(String rest) throws DukeException {
        String[] parts = rest.split("/by ", 2);
        if (parts.length < 2) {
            throw new DukeException("HEY! deadlines must have /by <date/time>");
        }
        return parts;
    }

    /**
     * Splits an "event" command's arguments on the "/from " and "/to " markers.
     *
     * @return a three-element array: the trimmed description, the raw "from" date
     *         text, and the raw "to" date text
     */
    public static String[] splitEventArgs(String rest) throws DukeException {
        int fromIndex = rest.indexOf("/from ");
        int toIndex = rest.indexOf("/to ");
        if (fromIndex == -1 || toIndex == -1) {
            throw new DukeException("HEY! events must use /from and /to <date/time>");
        }
        return new String[] {
            rest.substring(0, fromIndex).trim(),
            rest.substring(fromIndex + 6, toIndex),
            rest.substring(toIndex + 4)
        };
    }
}
