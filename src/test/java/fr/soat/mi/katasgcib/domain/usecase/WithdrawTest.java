package fr.soat.mi.katasgcib.domain.usecase;

import fr.soat.mi.katasgcib.domain.exception.ForbiddenAccountException;
import fr.soat.mi.katasgcib.domain.exception.NotFoundAccountException;
import fr.soat.mi.katasgcib.domain.model.Operation;
import fr.soat.mi.katasgcib.domain.repository.AccountRepository;
import fr.soat.mi.katasgcib.domain.repository.HistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class WithdrawTest {
    private Withdraw withdraw;
    private AccountRepository accountRepository;

    private HistoryRepository historyRepository;
    @BeforeEach
    void setup() {
        accountRepository = new FakeAccountRepository();
        historyRepository = new FakeHistoryRepository();
        withdraw = new Withdraw(accountRepository, historyRepository, new FakeLogger());
    }

    @Test
    void when_amount_to_withdraw_is_negative_should_throw_exception() {
        assertThatThrownBy(() -> withdraw.execute("account", -1.0))
                .isExactlyInstanceOf(ForbiddenAccountException.class)
                .hasMessage("The amount to withdraw can't be negative");
    }

    @Test
    void when_account_not_found_by_name_should_throw_exception() {
        assertThatThrownBy(() -> withdraw.execute("unknown", 1.0))
                .isExactlyInstanceOf(NotFoundAccountException.class)
                .hasMessage("The account unknown not found");
    }

    @Test
    void when_account_amount_is_less_than_amount_to_withdraw_should_throw_exception() {
        assertThatThrownBy(() -> withdraw.execute("jane", 101.0))
                .isExactlyInstanceOf(ForbiddenAccountException.class)
                .hasMessage("The account has less than the amount to withdraw");
    }

    @Test
    void should_update_account_with_account_amount_minus_given_amount() throws NotFoundAccountException, ForbiddenAccountException, IOException {
        var beforeWithdrawAccount = accountRepository.findByName("jane").orElseThrow();
        var amountBeforeWithdraw = beforeWithdrawAccount.amount();
        var amountToWithdraw = 10.0;

        withdraw.execute("jane", amountToWithdraw);

        accountRepository.findByName("jane").ifPresent(account -> assertThat(account.amount()).isEqualTo(amountBeforeWithdraw - amountToWithdraw));
    }

    @Test
    void after_withdraw_should_add_operation_in_history() throws IOException, NotFoundAccountException, ForbiddenAccountException {
        var beforeWithdrawAccount = accountRepository.findByName("jane").orElseThrow();
        var amountToWithdraw = 10.0;

        withdraw.execute("jane", amountToWithdraw);
        var withdrawOperation = historyRepository.getAllAccounts().stream().
                filter(operation -> operation.accountName().equals("jane")
                        && operation.amount().equals(10.0)
                        && operation.balance().equals(beforeWithdrawAccount.amount() - amountToWithdraw)
                )
                .findFirst();
        assertThat(withdrawOperation).isPresent();
    }
}