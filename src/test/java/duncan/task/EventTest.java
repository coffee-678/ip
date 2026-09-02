package duncan.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void toFileFormat_notDone_correctFileFormat() {
        Event event = new Event("project fair", LocalDate.of(2019, 12, 1), LocalDate.of(2019, 12, 2));

        assertEquals("E\t0\tproject fair\t2019-12-01\t2019-12-02", event.toFileFormat());
    }

    @Test
    public void toFileFormat_markedDone_correctFileFormat() {
        Event event = new Event("project fair", LocalDate.of(2019, 12, 1), LocalDate.of(2019, 12, 2));
        event.markAsDone();

        assertEquals("E\t1\tproject fair\t2019-12-01\t2019-12-02", event.toFileFormat());
    }

    @Test
    public void toString_notDone_correctDisplayFormat() {
        Event event = new Event("project fair", LocalDate.of(2019, 12, 1), LocalDate.of(2019, 12, 2));

        assertEquals("[E][ ] project fair (from: Dec 1 2019 to: Dec 2 2019)", event.toString());
    }

    @Test
    public void toString_markedDone_correctDisplayFormat() {
        Event event = new Event("project fair", LocalDate.of(2019, 12, 1), LocalDate.of(2019, 12, 2));
        event.markAsDone();

        assertEquals("[E][X] project fair (from: Dec 1 2019 to: Dec 2 2019)", event.toString());
    }
}
