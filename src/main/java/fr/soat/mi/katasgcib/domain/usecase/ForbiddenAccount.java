package fr.soat.mi.katasgcib.domain.usecase;

public class ForbiddenAccount extends Exception {
    public ForbiddenAccount(String message) {
        super(message);
    }
}
