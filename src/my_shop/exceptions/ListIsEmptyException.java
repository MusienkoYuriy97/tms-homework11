package my_shop.exceptions;

public class ListIsEmptyException extends Exception {
    public ListIsEmptyException() {
    }

    public ListIsEmptyException(String message) {
        super(message);
    }

}
