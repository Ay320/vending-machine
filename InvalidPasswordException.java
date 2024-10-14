/**
 * Exception thrown to indicate that an invalid password was provided.
 */
public class InvalidPasswordException extends RuntimeException{

    /**
     * Constructs a new InvalidPasswordException with the specified message.
     *
     * @param message The message (which is saved for later retrieval by the getMessage() method)
     */
   public InvalidPasswordException(String message){
    super(message);
   }
}