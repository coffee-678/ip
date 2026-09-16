package duncan;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import duncan.command.AddCommand;
import duncan.command.Command;
import duncan.command.DeleteCommand;
import duncan.command.ExitCommand;
import duncan.command.FindCommand;
import duncan.command.ListCommand;
import duncan.command.MarkCommand;
import duncan.command.RescheduleCommand;
import duncan.command.UnmarkCommand;
import duncan.task.Deadline;
import duncan.task.Event;
import duncan.task.Todo;

/**
 * Makes sense of the raw text the user types: splits a line into a command
 * word and arguments, validates and interprets those arguments, and
 * returns the {@link Command} that carries out what was typed.
 */
public class Parser {
    // Markers that separate a command's description from its date arguments.
    private static final String DEADLINE_BY_MARKER = "/by ";
    private static final String EVENT_FROM_MARKER = "/from ";
    private static final String EVENT_TO_MARKER = "/to ";

    private static final String MESSAGE_EMPTY_DESCRIPTION = "HEY! the description can't be left empty";

    /**
     * Parses one full line of console input into the {@link Command} it
     * represents.
     *
     * @throws DuncanException if the line is not a recognised command, or a
     *         recognised command's arguments are malformed
     */
    public static Command parse(String fullCommand) throws DuncanException {
        String commandWord = getCommandWord(fullCommand);
        String rest = getArguments(fullCommand);

        switch (commandWord) {
            case "list":
                return new ListCommand();
            case "find":
                return parseFind(rest);
            case "mark":
                return new MarkCommand(parseTaskIndex(rest));
            case "unmark":
                return new UnmarkCommand(parseTaskIndex(rest));
            case "delete":
                return new DeleteCommand(parseTaskIndex(rest));
            case "todo":
                return parseTodo(rest);
            case "deadline":
                return parseDeadline(rest);
            case "event":
                return parseEvent(rest);
            case "reschedule":
                return parseReschedule(rest);
            case "bye":
                return new ExitCommand();
            default:
                throw new DuncanException("HEY! idk what's that supposed to be");
        }
    }

    /** Parses a "find" command's arguments into the command that searches for the keyword. */
    private static Command parseFind(String rest) throws DuncanException {
        String keyword = rest.trim();
        if (keyword.isEmpty()) {
            throw new DuncanException("HEY! the keyword can't be left empty");
        }
        return new FindCommand(keyword);
    }

    /** Parses a "todo" command's arguments into the command that adds the todo. */
    private static Command parseTodo(String rest) throws DuncanException {
        String description = rest.trim();
        if (description.isEmpty()) {
            throw new DuncanException(MESSAGE_EMPTY_DESCRIPTION);
        }
        return new AddCommand(new Todo(description));
    }

    /** Parses a "deadline" command's arguments into the command that adds the deadline. */
    private static Command parseDeadline(String rest) throws DuncanException {
        String[] parts = splitDeadlineArgs(rest);
        String description = parts[0].trim();
        LocalDate by = parseDate(parts[1]);
        if (description.isEmpty()) {
            throw new DuncanException(MESSAGE_EMPTY_DESCRIPTION);
        }
        return new AddCommand(new Deadline(description, by));
    }

    /** Parses an "event" command's arguments into the command that adds the event. */
    private static Command parseEvent(String rest) throws DuncanException {
        String[] parts = splitEventArgs(rest);
        String description = parts[0].trim();
        LocalDate from = parseDate(parts[1]);
        LocalDate to = parseDate(parts[2]);
        if (description.isEmpty()) {
            throw new DuncanException(MESSAGE_EMPTY_DESCRIPTION);
        }
        return new AddCommand(new Event(description, from, to));
    }

    /**
     * Parses a "reschedule" command's arguments: a task number followed by either
     * "/by" and a date, or "/from" and "/to" with a date each, e.g. "2 /by 2019-12-09".
     *
     * <p>Which of the two forms is needed depends on the task's type, which only the
     * command can see. So a form that is missing, or has other text before its first
     * marker, is not an error here: its dates are left null for the command to report.
     *
     * @throws DuncanException if the task number is bad, or a date given is not yyyy-mm-dd
     */
    private static Command parseReschedule(String rest) throws DuncanException {
        String[] parts = rest.trim().split(" ", 2);
        int taskIndex = parseTaskIndex(parts[0]);
        String dateArgs = parts.length > 1 ? parts[1] : "";

        LocalDate by = null;
        int byIndex = dateArgs.indexOf(DEADLINE_BY_MARKER);
        if (byIndex != -1 && dateArgs.substring(0, byIndex).isBlank()) {
            by = parseDate(dateArgs.substring(byIndex + DEADLINE_BY_MARKER.length()));
        }

        LocalDate from = null;
        LocalDate to = null;
        int fromIndex = dateArgs.indexOf(EVENT_FROM_MARKER);
        int toIndex = dateArgs.indexOf(EVENT_TO_MARKER);
        boolean hasFromThenTo = fromIndex != -1 && toIndex > fromIndex;
        if (hasFromThenTo && dateArgs.substring(0, fromIndex).isBlank()) {
            from = parseDate(dateArgs.substring(fromIndex + EVENT_FROM_MARKER.length(), toIndex));
            to = parseDate(dateArgs.substring(toIndex + EVENT_TO_MARKER.length()));
        }

        return new RescheduleCommand(taskIndex, by, from, to);
    }

    /** Returns the first word of the input line, e.g. "todo" from "todo read book". */
    private static String getCommandWord(String input) {
        return input.split(" ", 2)[0];
    }

    /** Returns everything after the first word, or "" if there is nothing else. */
    private static String getArguments(String input) {
        String[] parts = input.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }

    /**
     * Parses a date typed by the user, e.g. "2019-12-02".
     *
     * @throws DuncanException if the text is not a valid yyyy-mm-dd date
     */
    private static LocalDate parseDate(String rest) throws DuncanException {
        try {
            return LocalDate.parse(rest.trim());
        } catch (DateTimeParseException e) {
            throw new DuncanException("HEY! dates must be in yyyy-mm-dd format");
        }
    }

    /**
     * Converts a task number typed by the user into a 0-based list index.
     * Only checks that the text is a positive whole number; whether that
     * number actually refers to a task in the current list is for the
     * command itself to check, since only it knows the list's current size.
     */
    private static int parseTaskIndex(String rest) throws DuncanException {
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(rest.trim());
        } catch (NumberFormatException e) {
            throw new DuncanException(Command.MESSAGE_INVALID_TASK_NUMBER);
        }
        if (taskNumber < 1) {
            throw new DuncanException(Command.MESSAGE_INVALID_TASK_NUMBER);
        }
        return taskNumber - 1;
    }

    /**
     * Splits a "deadline" command's arguments on the "/by " marker.
     *
     * @return a two-element array: the untrimmed description text, and the raw date text
     */
    private static String[] splitDeadlineArgs(String rest) throws DuncanException {
        String[] parts = rest.split(DEADLINE_BY_MARKER, 2);
        if (parts.length < 2) {
            throw new DuncanException(Command.MESSAGE_MISSING_BY);
        }
        return parts;
    }

    /**
     * Splits an "event" command's arguments on the "/from " and "/to " markers.
     *
     * @return a three-element array: the untrimmed description text, the raw "from"
     *         date text, and the raw "to" date text
     */
    private static String[] splitEventArgs(String rest) throws DuncanException {
        int fromIndex = rest.indexOf(EVENT_FROM_MARKER);
        int toIndex = rest.indexOf(EVENT_TO_MARKER);
        if (fromIndex == -1 || toIndex == -1) {
            throw new DuncanException(Command.MESSAGE_MISSING_FROM_TO);
        }
        return new String[] {
            rest.substring(0, fromIndex),
            rest.substring(fromIndex + EVENT_FROM_MARKER.length(), toIndex),
            rest.substring(toIndex + EVENT_TO_MARKER.length())
        };
    }
}
