package duncan;

/**
 * Signals a Duncan-specific error condition arising from invalid user
 * input (e.g. an unrecognized command, a malformed task description).
 */
public class DuncanException extends Exception {
    public DuncanException(String message) {
        super(message);
    }
}
