package duncan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import duncan.task.Deadline;
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
    public void showTaskRescheduled_deadline_oldAndNewTaskShown() {
        Ui ui = new Ui();

        ui.showTaskRescheduled("[D][ ] return book (by: Dec 2 2019)",
                new Deadline("return book", LocalDate.of(2019, 12, 9)));

        assertEquals("OK, I've rescheduled this task:" + NEWLINE
                + "  from: [D][ ] return book (by: Dec 2 2019)" + NEWLINE
                + "  to:   [D][ ] return book (by: Dec 9 2019)" + NEWLINE,
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
