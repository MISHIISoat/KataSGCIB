package fr.soat.mi.katasgcib.infra.repository;

import fr.soat.mi.katasgcib.domain.model.Account;
import fr.soat.mi.katasgcib.infra.parser.AccountParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class AccountRepositoryImplTest {
    private AccountRepositoryImpl accountRepository;

    AccountParser mockAccountParser;

    @BeforeEach
    void setup() {
        mockAccountParser = Mockito.mock(AccountParser.class);
        accountRepository = new AccountRepositoryImpl(mockAccountParser);
    }

    @Nested
    class ExistsByName {
        @Test
        void when_parser_return_empty_list_should_return_false() throws IOException {
            when(mockAccountParser.getAllAccount()).thenReturn(new ArrayList<>());

            assertThat(accountRepository.existsByName("accountName")).isFalse();
        }

        @Test
        void when_the_given_account_name_is_not_present_to_account_list_should_return_false() throws IOException {
            when(mockAccountParser.getAllAccount()).thenReturn(List.of(new Account("name", 12.5)));

            assertThat(accountRepository.existsByName("unknownAccountName")).isFalse();
        }

        @Test
        void when_the_given_account_name_is_present_to_account_list_should_return_true() throws IOException {
            when(mockAccountParser.getAllAccount()).thenReturn(List.of(new Account("username", 124.5), new Account("name", 12.5)));

            assertThat(accountRepository.existsByName("username")).isTrue();
        }
    }


    @Nested
    class FindByName {
        @Test
        void when_account_list_is_empty_should_return_empty_optional() throws IOException {
            when(mockAccountParser.getAllAccount()).thenReturn(List.of());

            assertThat(accountRepository.findByName("name")).isEmpty();
        }

        @Test
        void when_account_list_no_contain_name_with_given_account_name_should_return_empty_optional() throws IOException {
            when(mockAccountParser.getAllAccount()).thenReturn(List.of(new Account("username", 124.5), new Account("name", 12.5)));

            assertThat(accountRepository.findByName("unknownAccountName")).isEmpty();
        }

        @Test
        void when_account_list_contain_name_with_given_account_name_should_return_account() throws IOException {
            when(mockAccountParser.getAllAccount()).thenReturn(List.of(new Account("username", 124.5), new Account("name", 12.5)));

            var expectedOptional = Optional.of(new Account("name", 12.5));
            assertThat(accountRepository.findByName("name")).isEqualTo(expectedOptional);
        }
    }

    @Nested
    class Add {
        @Test
        void when_account_list_is_empty_should_add_to_the_list() throws IOException {
            when(mockAccountParser.getAllAccount()).thenReturn(List.of());
            var newAccount = new Account("newAccount", 1354D);

            accountRepository.add(newAccount);

            verify(mockAccountParser, times(1)).saveAllAccounts(List.of(newAccount));
        }

        @Test
        void when_account_list_contain_accounts_should_add_new_account_at_the_end_of_account_list() throws IOException {
            var account1 = new Account("name", 1523D);
            var account2 = new Account("account2", 354D);
            when(mockAccountParser.getAllAccount()).thenReturn(List.of(account1, account2));

            var newAccount = new Account("newAccount", 1354D);

            accountRepository.add(newAccount);

            verify(mockAccountParser, times(1)).saveAllAccounts(List.of(account1, account2, newAccount));
        }
    }

    @Nested
    class UpdateAmount {
        @Test
        void should_update_account_with_same_name_in_account_list() throws IOException {
            var account1 = new Account("name", 1523D);
            var account2 = new Account("account2", 354D);
            when(mockAccountParser.getAllAccount()).thenReturn(List.of(account1, account2));
            var accountToUpdate = new Account("name", 600D);

            accountRepository.update(accountToUpdate);

            verify(mockAccountParser, times(1)).saveAllAccounts(List.of(accountToUpdate, account2));
        }
    }
}