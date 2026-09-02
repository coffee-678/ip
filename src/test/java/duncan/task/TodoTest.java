package duncan.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TodoTest {
    // ---- Todo's own methods ----

    @Test
    public void toFileFormat_notDone_correctFileFormat() {
        Todo todo = new Todo("read book");

        assertEquals("T\t0\tread book", todo.toFileFormat());
    }

    @Test
    public void toFileFormat_markedDone_correctFileFormat() {
        Todo todo = new Todo("read book");
        todo.markAsDone();

        assertEquals("T\t1\tread book", todo.toFileFormat());
    }

    @Test
    public void toString_notDone_correctDisplayFormat() {
        Todo todo = new Todo("read book");

        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void toString_markedDone_correctDisplayFormat() {
        Todo todo = new Todo("read book");
        todo.markAsDone();

        assertEquals("[T][X] read book", todo.toString());
    }

    // ---- inherited from Task, exercised via its simplest concrete subclass ----

    @Test
    public void getDescription_returnsConstructorValue() {
        Todo todo = new Todo("read book");

        assertEquals("read book", todo.getDescription());
    }

    @Test
    public void getStatusIcon_newTask_notDone() {
        Todo todo = new Todo("read book");

        assertEquals(" ", todo.getStatusIcon());
    }

    @Test
    public void markAsDone_notDoneTask_statusIconBecomesX() {
        Todo todo = new Todo("read book");

        todo.markAsDone();

        assertEquals("X", todo.getStatusIcon());
    }

    @Test
    public void markAsNotDone_doneTask_statusIconBecomesBlank() {
        Todo todo = new Todo("read book");
        todo.markAsDone();

        todo.markAsNotDone();

        assertEquals(" ", todo.getStatusIcon());
    }
}
