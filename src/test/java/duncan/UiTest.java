package duncan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import duncan.task.Todo;

public class UiTest {
    private static final String NEWLINE = System.lineSeparator();

    @Test
    public void flushOutput_nothingShown_emptyStringReturned() {
        assertEquals("", new Ui().flushOutput());
    }

    @Test
    public void flushOutput_severalMessages_linesReturnedInOrder() {
        Ui ui = new Ui();

        ui.showTaskAdded(new Todo("read book"), 1);
        ui.showGoodbye();

        assertEquals("Got it. I've added this task:" + NEWLINE
                + "  [T][ ] read book" + NEWLINE
                + "Now you have 1 tasks in the list." + NEWLINE
                + "Bye. Hope to see you again soon!" + NEWLINE,
                ui.flushOutput());
    }

    @Test
    public void flushOutput_calledTwice_bufferClearedAfterFirstCall() {
        Ui ui = new Ui();
        ui.showError("HEY! this task number is bad");

        assertEquals("HEY! this task number is bad" + NEWLINE, ui.flushOutput());
        assertEquals("", ui.flushOutput());
    }
}
