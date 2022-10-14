package fr.soat.mi.katasgcib.domain.usecase;

import fr.soat.mi.katasgcib.domain.exception.ForbiddenAccountException;
import fr.soat.mi.katasgcib.domain.model.Account;
import fr.soat.mi.katasgcib.domain.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DepositTest {
    private Deposit makeADeposit;
    private AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = new FakeAccountRepository();
        makeADeposit = new Deposit(accountRepository, new FakeLogger());
    }

    @Test
    void when_account_not_exist_should_add_account_with_amount() throws IOException, ForbiddenAccountException {
        makeADeposit.execute("newUser", 12.5);

        var result = accountRepository.findByName("newUser");
        assertThat(result).isNotEmpty();
        var expectedAccount = new Account("newUser", 12.5);
        assertThat(result.get()).isEqualTo(expectedAccount);
    }

    @Test
    void when_account_exist_should_add_amount_in_existing_account() throws IOException, ForbiddenAccountException {
        makeADeposit.execute("jane", 12.5);

        var result = accountRepository.findByName("jane");
        assertThat(result).isNotEmpty();
        var expectedAccount = new Account("jane", 112.5);
        assertThat(result.get()).isEqualTo(expectedAccount);
    }

    @Test
    void when_amount_to_deposit_is_negative_should_throw_exception() {
        assertThatThrownBy(() -> makeADeposit.execute("jane", -12.5))
                .isExactlyInstanceOf(ForbiddenAccountException.class)
                .hasMessage("The amount to deposit can't be negative");
    }
}