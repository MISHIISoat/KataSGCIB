package fr.soat.mi.katasgcib.infra.parser;

import fr.soat.mi.katasgcib.Application;
import fr.soat.mi.katasgcib.domain.model.Account;
import fr.soat.mi.katasgcib.infra.logger.ConsoleLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AccountParserImplTest {
    private AccountParserImpl accountParser;
    private FileReader fileReader;
    private FileWriter fileWriter;

    @BeforeEach
    void setup() {
        var logger = new ConsoleLogger();
        fileReader = new DefaultFileReader(logger);
        fileWriter = new DefaultFileWriter();
        accountParser = new AccountParserImpl(fileReader, fileWriter);
    }

    @Nested
    class GetAllAccount {
        @Test
        void should_read_accounts_file_and_map_content_file_to_account_list() throws IOException {
            var content = "name;amount" + System.lineSeparator()
                    + "username;15.2" + System.lineSeparator()
                    + "name2;100.0";
            fileWriter.writeContentToFile(content, Application.ACCOUNTS_FILE);

            var accountList = accountParser.getAllAccount();

            var expectedList = List.of(
                    new Account("username", 15.2),
                    new Account("name2", 100.0)
            );
            assertThat(accountList).isEqualTo(expectedList);
        }
    }

    @Nested
    class SaveAllAccount {
        @Test
        void should_save_all_accounts_in_accounts_csv_file() throws IOException {
            var accountList = List.of(
                    new Account("accountName", 156.3),
                    new Account("account2", 254.4)
            );

            accountParser.saveAllAccounts(accountList);

            var content = fileReader.readTextFile(Application.ACCOUNTS_FILE);

            var expectedContent = "name;amount" + System.lineSeparator() +
                    "accountName;156.3" + System.lineSeparator() +
                    "account2;254.4" + System.lineSeparator();
            assertThat(content).isEqualTo(expectedContent);
        }
    }
}