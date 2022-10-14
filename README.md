## Kata SGCIB

Prerequisite:

- Java 17
- Maven

Before to run the kata you need to compile the application with this command :

```
mvn clean package
```

### User Stories

##### US 1:

In order to save money

As a bank client

I want to make a deposit in my account

Example of command to deposit 10€ in user account
```
java -jar target/katasgcib-1.0-SNAPSHOT-shaded.jar deposit 10 user
```

·         US 2:

In order to retrieve some or all of my savings

As a bank client

I want to make a withdrawal from my account

Example of command to withdraw 20€ in john account
```
java -jar target/katasgcib-1.0-SNAPSHOT-shaded.jar withdraw 20 user
```

·         US 3:

In order to check my operations

As a bank client

I want to see the history (operation, date, amount, balance) of my operations"

Example of command to see the history of doe's account
```
java -jar target/katasgcib-1.0-SNAPSHOT-shaded.jar history doe
```