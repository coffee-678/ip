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
import duncan.command.RescheduleCommand;
import duncan.command.SnoozeCommand;
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
        DuncanException e = assertThrows(DuncanException.class, () ->
                Parser.parse("deadline return book /by not-a-date"));

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
        DuncanException e = assertThrows(DuncanException.class, () ->
                Parser.parse("event project fair /to 2019-12-02"));

        assertEquals("HEY! events must use /from and /to <date/time>", e.getMessage());
    }

    @Test
    public void parse_eventMissingTo_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () ->
                Parser.parse("event project fair /from 2019-12-01"));

        assertEquals("HEY! events must use /from and /to <date/time>", e.getMessage());
    }

    @Test
    public void parse_eventInvalidDateFormat_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () ->
                Parser.parse("event project fair /from not-a-date /to 2019-12-02"));

        assertEquals("HEY! dates must be in yyyy-mm-dd format", e.getMessage());
    }

    @Test
    public void parse_eventEmptyDescription_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () ->
                Parser.parse("event /from 2019-12-01 /to 2019-12-02"));

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

    // ---- reschedule ----

    @Test
    public void parse_rescheduleWithBy_rescheduleCommandReturned() throws DuncanException {
        assertInstanceOf(RescheduleCommand.class, Parser.parse("reschedule 2 /by 2019-12-09"));
    }

    @Test
    public void parse_rescheduleWithFromAndTo_rescheduleCommandReturned() throws DuncanException {
        assertInstanceOf(RescheduleCommand.class, Parser.parse("reschedule 3 /from 2019-12-05 /to 2019-12-06"));
    }

    @Test
    public void parse_rescheduleWithNoDates_rescheduleCommandReturned() throws DuncanException {
        // Whether dates are missing depends on the task's type, so the command reports it, not the parser.
        assertInstanceOf(RescheduleCommand.class, Parser.parse("reschedule 2"));
    }

    @Test
    public void parse_rescheduleWithBadTaskNumber_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () ->
                Parser.parse("reschedule two /by 2019-12-09"));

        assertEquals("HEY! this task number is bad", e.getMessage());
    }

    @Test
    public void parse_rescheduleWithBadByDate_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () ->
                Parser.parse("reschedule 2 /by next week"));

        assertEquals("HEY! dates must be in yyyy-mm-dd format", e.getMessage());
    }

    @Test
    public void parse_rescheduleWithBadToDate_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () ->
                Parser.parse("reschedule 3 /from 2019-12-05 /to someday"));

        assertEquals("HEY! dates must be in yyyy-mm-dd format", e.getMessage());
    }

    // ---- snooze ----

    @Test
    public void parse_snoozeWithValidNumberAndDays_snoozeCommandReturned() throws DuncanException {
        assertInstanceOf(SnoozeCommand.class, Parser.parse("snooze 2 7"));
    }

    @Test
    public void parse_snoozeWithBadTaskNumber_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () -> Parser.parse("snooze two 7"));

        assertEquals("HEY! this task number is bad", e.getMessage());
    }

    @Test
    public void parse_snoozeWithZeroDays_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () -> Parser.parse("snooze 2 0"));

        assertEquals("HEY! the number of days is bad", e.getMessage());
    }

    @Test
    public void parse_snoozeWithNegativeDays_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () -> Parser.parse("snooze 2 -3"));

        assertEquals("HEY! the number of days is bad", e.getMessage());
    }

    @Test
    public void parse_snoozeWithNonNumericDays_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () -> Parser.parse("snooze 2 week"));

        assertEquals("HEY! the number of days is bad", e.getMessage());
    }

    @Test
    public void parse_snoozeWithDaysMissing_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () -> Parser.parse("snooze 2"));

        assertEquals("HEY! the number of days is bad", e.getMessage());
    }

    @Test
    public void parse_snoozeWithDaysTooLargeForInt_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () -> Parser.parse("snooze 2 99999999999"));

        assertEquals("HEY! the number of days is bad", e.getMessage());
    }

    // ---- unrecognised ----

    @Test
    public void parse_unrecognisedCommandWord_exceptionThrown() {
        DuncanException e = assertThrows(DuncanException.class, () -> Parser.parse("blah"));

        assertEquals("HEY! idk what's that supposed to be", e.getMessage());
    }
}
