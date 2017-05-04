package pt.upa.broker.ws.exception;

/**
 * Created by lolstorm on 26/04/16.
 */
public class BrokerWSException extends Exception {

    private static final long serialVersionUID = 1L;

    public BrokerWSException() {
        super();
    }

    public BrokerWSException(String message) {
        super(message);
    }

    public BrokerWSException(Throwable cause) {
        super(cause);
    }

    public BrokerWSException(String message, Throwable cause) {
        super(message, cause);
    }
}
