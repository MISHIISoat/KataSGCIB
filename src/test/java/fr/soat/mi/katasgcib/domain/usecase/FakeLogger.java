package fr.soat.mi.katasgcib.domain.usecase;

import fr.soat.mi.katasgcib.infra.logger.Logger;

public class FakeLogger implements Logger {
    @Override
    public void out(String output) {

    }

    @Override
    public void err(String message) {

    }
}
