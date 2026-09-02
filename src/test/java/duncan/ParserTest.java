package duncan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import duncan.command.AddCommand;
import duncan.command.Command;
import duncan.command.DeleteCommand;
import duncan.command.ExitCommand;
import duncan.command.ListCommand;
import duncan.command.MarkCommand;
import duncan.command.UnmarkCommand;

public class ParserTest {
    // ---- list / bye ----

    @Test
    public void parse_list_listCommandReturned() throws DuncanException {
        assertInstanceOf(ListCommand.class, Parser.parse("list"));
    }

    @Test
    public void parse_bye_exitCommandWithIsExitTrueReturned() throws DuncanException {
        Command command = Parser.parse("bye");

        assertInstanceOf(ExitCommand.class, command);
        assertTrue(command.isExit());
    }

    // ---- todo ----

    @Test
    public void parse_todoWithDescription_addCommandReturned() throws DuncanException {
        assertInstanceOf(AddCommand.class, Parser.parse("todo read book"));
    }

    @Test
    public void parse_todoWithNoDescription_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () -> Parser.parse("todo"));

        assertEquals("HEY! the description can't be left empty", e.getMessage());
    }

    @Test
    public void parse_todoWithBlankDescription_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () -> Parser.parse("todo    "));

        assertEquals("HEY! the description can't be left empty", e.getMessage());
    }

    // ---- deadline ----

    @Test
    public void parse_deadlineWithValidArgs_addCommandReturned() throws DuncanException {
        assertInstanceOf(AddCommand.class, Parser.parse("deadline return book /by 2019-12-02"));
    }

    @Test
    public void parse_deadlineMissingBy_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () -> Parser.parse("deadline return book"));

        assertEquals("HEY! deadlines must have /by <date/time>", e.getMessage());
    }

    @Test
    public void parse_deadlineInvalidDateFormat_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class,
                () -> Parser.parse("deadline return book /by not-a-date"));

        assertEquals("HEY! dates must be in yyyy-mm-dd format", e.getMessage());
    }

    @Test
    public void parse_deadlineEmptyDescription_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () -> Parser.parse("deadline /by 2019-12-02"));

        assertEquals("HEY! the description can't be left empty", e.getMessage());
    }

    // ---- event ----

    @Test
    public void parse_eventWithValidArgs_addCommandReturned() throws DuncanException {
        assertInstanceOf(AddCommand.class,
                Parser.parse("event project fair /from 2019-12-01 /to 2019-12-02"));
    }

    @Test
    public void parse_eventMissingFrom_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class,
                () -> Parser.parse("event project fair /to 2019-12-02"));

        assertEquals("HEY! events must use /from and /to <date/time>", e.getMessage());
    }

    @Test
    public void parse_eventMissingTo_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class,
                () -> Parser.parse("event project fair /from 2019-12-01"));

        assertEquals("HEY! events must use /from and /to <date/time>", e.getMessage());
    }

    @Test
    public void parse_eventInvalidDateFormat_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class,
                () -> Parser.parse("event project fair /from not-a-date /to 2019-12-02"));

        assertEquals("HEY! dates must be in yyyy-mm-dd format", e.getMessage());
    }

    @Test
    public void parse_eventEmptyDescription_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class,
                () -> Parser.parse("event /from 2019-12-01 /to 2019-12-02"));

        assertEquals("HEY! the description can't be left empty", e.getMessage());
    }

    // ---- mark / unmark / delete ----

    @Test
    public void parse_markWithValidNumber_markCommandReturned() throws DuncanException {
        assertInstanceOf(MarkCommand.class, Parser.parse("mark 1"));
    }

    @Test
    public void parse_markWithNonNumericArgument_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () -> Parser.parse("mark abc"));

        assertEquals("HEY! this task number is bad", e.getMessage());
    }

    @Test
    public void parse_markWithZero_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () -> Parser.parse("mark 0"));

        assertEquals("HEY! this task number is bad", e.getMessage());
    }

    @Test
    public void parse_markWithNegativeNumber_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () -> Parser.parse("mark -1"));

        assertEquals("HEY! this task number is bad", e.getMessage());
    }

    @Test
    public void parse_unmarkWithValidNumber_unmarkCommandReturned() throws DuncanException {
        assertInstanceOf(UnmarkCommand.class, Parser.parse("unmark 2"));
    }

    @Test
    public void parse_deleteWithValidNumber_deleteCommandReturned() throws DuncanException {
        assertInstanceOf(DeleteCommand.class, Parser.parse("delete 3"));
    }

    // ---- unrecognised ----

    @Test
    public void parse_unrecognisedCommandWord_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () -> Parser.parse("blah"));

        assertEquals("HEY! idk what's that supposed to be", e.getMessage());
    }
}
