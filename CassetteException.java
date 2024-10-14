/**
 * Exception thrown to indicate that there are insufficient funds in the cassette.
 */
public class CassetteException extends RuntimeException{

    /**
     * Constructs a new CassetteException with the specified message.
     *
     * @param message The message (which is saved for later retrieval by the getMessage() method)
     */
    public CassetteException(String message){
        super(message);
    }
}