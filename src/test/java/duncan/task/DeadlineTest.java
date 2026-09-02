package duncan.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DeadlineTest {
    @Test
    public void toFileFormat_notDone_correctFileFormat() {
        Deadline deadline = new Deadline("return book", LocalDate.of(2019, 12, 2));

        assertEquals("D\t0\treturn book\t2019-12-02", deadline.toFileFormat());
    }

    @Test
    public void toFileFormat_markedDone_correctFileFormat() {
        Deadline deadline = new Deadline("return book", LocalDate.of(2019, 12, 2));
        deadline.markAsDone();

        assertEquals("D\t1\treturn book\t2019-12-02", deadline.toFileFormat());
    }

    @Test
    public void toString_notDone_correctDisplayFormat() {
        Deadline deadline = new Deadline("return book", LocalDate.of(2019, 12, 2));

        assertEquals("[D][ ] return book (by: Dec 2 2019)", deadline.toString());
    }

    @Test
    public void toString_markedDone_correctDisplayFormat() {
        Deadline deadline = new Deadline("return book", LocalDate.of(2019, 12, 2));
        deadline.markAsDone();

        assertEquals("[D][X] return book (by: Dec 2 2019)", deadline.toString());
    }
}
