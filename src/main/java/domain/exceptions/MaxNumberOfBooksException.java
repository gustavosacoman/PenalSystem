package domain.exceptions;

public class MaxNumberOfBooksException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public MaxNumberOfBooksException() {
        super("The max number of books has been reached for this prisoner.");
    }
}