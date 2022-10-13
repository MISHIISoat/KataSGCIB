package fr.soat.mi.katasgcib.infra.logger;

public class ConsoleLogger implements Logger {
    @Override
    public void out(String output) {
        System.out.println(output);
    }
}
