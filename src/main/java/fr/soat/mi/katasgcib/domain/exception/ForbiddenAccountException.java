package fr.soat.mi.katasgcib.domain.exception;

public class ForbiddenAccountException extends Exception {
    public ForbiddenAccountException(String message) {
        super(message);
    }
}
