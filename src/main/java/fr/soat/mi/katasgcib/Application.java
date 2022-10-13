package fr.soat.mi.katasgcib;

public class Application {

    final public static String ACCOUNTS_FILE = "accounts.csv";

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Not enough arguments");
        }
        var action = args[0];
        if (action.equals("deposit") && args.length != 3) {
            throw new IllegalArgumentException("Deposit need 3 arguments");
        }


    }
}
