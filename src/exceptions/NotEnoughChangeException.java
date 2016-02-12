
package exceptions;

/**
 * Exception thrown when the machine does not have enough change.
 * @author Kenneth
 */
public class NotEnoughChangeException extends Exception {

    /**
     * Creates a new instance of <code>NotEnoughChangeException</code> without
     * detail message.
     */
    public NotEnoughChangeException() {
    }

    /**
     * Constructs an instance of <code>NotEnoughChangeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NotEnoughChangeException(String msg) {
        super(msg);
    }
}
