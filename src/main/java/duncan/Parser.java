package duncan;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import duncan.command.AddCommand;
import duncan.command.Command;
import duncan.command.DeleteCommand;
import duncan.command.ExitCommand;
import duncan.command.ListCommand;
import duncan.command.MarkCommand;
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
    /**
     * Parses one full line of console input into the {@link Command} it
     * represents.
     *
     * @throws DukeException if the line is not a recognised command, or a
     *         recognised command's arguments are malformed
     */
    public static Command parse(String fullCommand) throws DukeException {
        String commandWord = getCommandWord(fullCommand);
        String rest = getArguments(fullCommand);

        switch (commandWord) {
        case "list":
            return new ListCommand();
        case "mark":
            return new MarkCommand(parseTaskIndex(rest));
        case "unmark":
            return new UnmarkCommand(parseTaskIndex(rest));
        case "delete":
            return new DeleteCommand(parseTaskIndex(rest));
        case "todo": {
            String description = rest.trim();
            if (description.isEmpty()) {
                throw new DukeException("HEY! the description can't be left empty");
            }
            return new AddCommand(new Todo(description));
        }
        case "deadline": {
            String[] parts = splitDeadlineArgs(rest);
            String description = parts[0].trim();
            LocalDate by = parseDate(parts[1]);
            if (description.isEmpty()) {
                throw new DukeException("HEY! the description can't be left empty");
            }
            return new AddCommand(new Deadline(description, by));
        }
        case "event": {
            String[] parts = splitEventArgs(rest);
            String description = parts[0];
            LocalDate from = parseDate(parts[1]);
            LocalDate to = parseDate(parts[2]);
            if (description.isEmpty()) {
                throw new DukeException("HEY! the description can't be left empty");
            }
            return new AddCommand(new Event(description, from, to));
        }
        case "bye":
            return new ExitCommand();
        default:
            throw new DukeException("HEY! idk what's that supposed to be");
        }
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

    private static LocalDate parseDate(String rest) throws DukeException {
        try {
            return LocalDate.parse(rest.trim());
        } catch (DateTimeParseException e) {
            throw new DukeException("HEY! dates must be in yyyy-mm-dd format");
        }
    }

    /**
     * Converts a task number typed by the user into a 0-based list index.
     * Only checks that the text is a positive whole number; whether that
     * number actually refers to a task in the current list is for the
     * command itself to check, since only it knows the list's current size.
     */
    private static int parseTaskIndex(String rest) throws DukeException {
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(rest.trim());
        } catch (NumberFormatException e) {
            throw new DukeException("HEY! this task number is bad");
        }
        if (taskNumber < 1) {
            throw new DukeException("HEY! this task number is bad");
        }
        return taskNumber - 1;
    }

    /**
     * Splits a "deadline" command's arguments on the "/by " marker.
     *
     * @return a two-element array: the untrimmed description text, and the raw date text
     */
    private static String[] splitDeadlineArgs(String rest) throws DukeException {
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
    private static String[] splitEventArgs(String rest) throws DukeException {
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
