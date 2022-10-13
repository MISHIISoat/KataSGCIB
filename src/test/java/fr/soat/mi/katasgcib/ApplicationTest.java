package fr.soat.mi.katasgcib;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ApplicationTest {

    @Nested
    class Deposit {
        @Test
        void when_account_csv_not_exist_and_deposit_15_to_user_account_should_create_accounts_csv_with_user_account_to_15() throws Exception {
            if (!Files.deleteIfExists(Path.of(Application.ACCOUNTS_FILE))) {
                throw new Exception("Problem to delete file");
            }

            Application.main(new String[]{"deposit", "10", "user"});

            var file = new File(Application.ACCOUNTS_FILE);
            assertThat(file.exists()).isTrue();
            var content = Files.readString(Path.of(Application.ACCOUNTS_FILE));
            var expectedResult = "name;account" + System.lineSeparator()
                    + "10;user" + System.lineSeparator();

            assertThat(content).isEqualTo(expectedResult);
        }

        @Test
        void when_account_csv_contain_user_account_to_10_when_user_deposit_15_user_account_should_be_25() throws Exception {
            var fileWriter = new FileWriter(Application.ACCOUNTS_FILE);
            var contentFile = "name;account" + System.lineSeparator()
                    + "john;100" + System.lineSeparator()
                    + "user;10" + System.lineSeparator()
                    + "doe;99" + System.lineSeparator();
            fileWriter.write(contentFile);

            Application.main(new String[]{"deposit", "15", "user"});

            var file = new File(Application.ACCOUNTS_FILE);
            assertThat(file.exists()).isTrue();
            var content = Files.readString(Path.of(Application.ACCOUNTS_FILE));
            var expectedResult = "name;account" + System.lineSeparator()
                    + "john;100" + System.lineSeparator()
                    + "user;15" + System.lineSeparator()
                    + "doe;99" + System.lineSeparator();

            assertThat(content).isEqualTo(expectedResult);
        }
    }

}