package fr.soat.mi.katasgcib;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ApplicationIT {

    @Nested
    class Deposit {
        @Test
        void when_account_csv_not_exist_and_deposit_15_to_user_account_should_create_accounts_csv_with_user_account_to_15() throws Exception {
            Path accountFilePath = Path.of(Application.ACCOUNTS_FILE);
            if (!Files.deleteIfExists(accountFilePath)) {
                System.out.println("File not delete");
            }

            Application.main(new String[]{"deposit", "10", "user"});

            var file = new File(Application.ACCOUNTS_FILE);
            assertThat(file.exists()).isTrue();
            var content = Files.readString(accountFilePath);
            var expectedResult = "name;amount" + System.lineSeparator()
                    + "user;10.0" + System.lineSeparator();

            assertThat(content).isEqualTo(expectedResult);
        }

        @Test
        void when_account_csv_contain_user_account_to_10_when_user_deposit_15_user_account_should_be_25() throws Exception {
            try (var fileWriter = new FileWriter(Application.ACCOUNTS_FILE)) {
                var contentFile = "name;amount" + System.lineSeparator()
                        + "john;100.0" + System.lineSeparator()
                        + "user;10.0" + System.lineSeparator()
                        + "doe;99.0" + System.lineSeparator();
                fileWriter.write(contentFile);
            }
            Application.main(new String[]{"deposit", "15", "user"});

            var file = new File(Application.ACCOUNTS_FILE);
            assertThat(file.exists()).isTrue();
            var content = Files.readString(Path.of(Application.ACCOUNTS_FILE));
            var expectedResult = "name;amount" + System.lineSeparator()
                    + "john;100.0" + System.lineSeparator()
                    + "user;25.0" + System.lineSeparator()
                    + "doe;99.0" + System.lineSeparator();

            assertThat(content).isEqualTo(expectedResult);
        }
    }


    @Nested
    class Withdraw {
        @Test
        void when_account_csv_contain_user_account_to_10_when_user_withdraw_5_user_account_should_be_5() throws Exception {
            try (var fileWriter = new FileWriter(Application.ACCOUNTS_FILE)) {
                var contentFile = "name;amount" + System.lineSeparator()
                        + "john;100.0" + System.lineSeparator()
                        + "user;10.0" + System.lineSeparator()
                        + "doe;99.0" + System.lineSeparator();
                fileWriter.write(contentFile);
            }
            Application.main(new String[]{"withdraw", "5", "user"});

            var file = new File(Application.ACCOUNTS_FILE);
            assertThat(file.exists()).isTrue();
            var content = Files.readString(Path.of(Application.ACCOUNTS_FILE));
            var expectedResult = "name;amount" + System.lineSeparator()
                    + "john;100.0" + System.lineSeparator()
                    + "user;5.0" + System.lineSeparator()
                    + "doe;99.0" + System.lineSeparator();

            assertThat(content).isEqualTo(expectedResult);
        }
    }

    @Nested
    class History {
        @Test
        void given_user_deposit_and_withdraw_when_user_want_to_get_history_should_show_all_given_action() throws IOException {
            Path historyFilePath = Path.of(Application.HISTORY_FILE);
            if (!Files.deleteIfExists(historyFilePath)) {
                System.out.println("File not delete");
            }
            Path accountFilePath = Path.of(Application.ACCOUNTS_FILE);
            if (!Files.deleteIfExists(accountFilePath)) {
                System.out.println("File not delete");
            }
            try (var fileWriter = new FileWriter(Application.ACCOUNTS_FILE)) {
                var contentFile = "name;amount" + System.lineSeparator()
                        + "john;100.0" + System.lineSeparator()
                        + "user;10.0" + System.lineSeparator()
                        + "doe;99.0" + System.lineSeparator();
                fileWriter.write(contentFile);

                Application.main(new String[]{"deposit", "15", "user"});
                Application.main(new String[]{"withdraw", "5", "user"});


                Application.main(new String[]{"history", "user"});

                var file = new File(Application.HISTORY_FILE);
                assertThat(file.exists()).isTrue();
                var content = Files.readString(historyFilePath);
                var contentLines = content.split(System.lineSeparator());
                System.out.println(Arrays.toString(contentLines));

                assertThat(contentLines[1]).contains("deposit", "user", "15.0", "25.0");
                assertThat(contentLines[2]).contains("withdraw", "user", "5.0", "20.0");
            }
        }
    }
}