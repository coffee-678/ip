package duncan;

/**
 * Signals a Duncan-specific error condition arising from invalid user
 * input (e.g. an unrecognized command, a malformed task description).
 */
public class DukeException extends Exception {
    public DukeException(String message) {
        super(message);
    }
}
