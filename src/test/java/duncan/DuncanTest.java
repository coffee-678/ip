package duncan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class DuncanTest {
    private static final String NEWLINE = System.lineSeparator();

    @TempDir
    Path tempDir;

    /** Returns a Duncan saving to this test's temporary directory, so tests do not share a save file. */
    private Duncan createDuncan() {
        return new Duncan(tempDir.resolve("tasks.txt").toString());
    }

    @Test
    public void getResponse_addTodo_taskAddedMessageReturned() {
        assertEquals("Got it. I've added this task:" + NEWLINE
                + "  [T][ ] read book" + NEWLINE
                + "Now you have 1 tasks in the list." + NEWLINE,
                createDuncan().getResponse("todo read book"));
    }

    @Test
    public void getResponse_unknownCommand_errorMessageReturned() {
        assertEquals("HEY! idk what's that supposed to be" + NEWLINE,
                createDuncan().getResponse("blah"));
    }

    @Test
    public void getResponse_secondCommand_onlyThatCommandsReplyReturned() {
        Duncan duncan = createDuncan();
        duncan.getResponse("todo read book");

        assertEquals("Here are the tasks in your list:" + NEWLINE
                + "1.[T][ ] read book" + NEWLINE,
                duncan.getResponse("list"));
    }

    @Test
    public void isExit_byeCommand_trueOnlyAfterBye() {
        Duncan duncan = createDuncan();
        assertFalse(duncan.isExit());

        duncan.getResponse("list");
        assertFalse(duncan.isExit());

        duncan.getResponse("bye");
        assertTrue(duncan.isExit());
    }

    @Test
    public void getWelcome_freshSession_greetingWithoutBannerReturned() {
        assertEquals("Hello! I'm Duncan." + NEWLINE
                + "What can I do for you?" + NEWLINE,
                createDuncan().getWelcome());
    }

    @Test
    public void getResponse_rescheduleDeadlineWithTextBeforeBy_missingByMessageReturned() {
        Duncan duncan = createDuncan();
        duncan.getResponse("deadline return book /by 2019-12-02");

        assertEquals("HEY! deadlines must have /by <date/time>" + NEWLINE,
                duncan.getResponse("reschedule 1 soon /by 2019-12-09"));
    }

    @Test
    public void getResponse_rescheduleEventWithToBeforeFrom_missingFromToMessageReturned() {
        Duncan duncan = createDuncan();
        duncan.getResponse("event project fair /from 2019-12-01 /to 2019-12-02");

        assertEquals("HEY! events must use /from and /to <date/time>" + NEWLINE,
                duncan.getResponse("reschedule 1 /to 2019-12-06 /from 2019-12-05"));
    }
}
