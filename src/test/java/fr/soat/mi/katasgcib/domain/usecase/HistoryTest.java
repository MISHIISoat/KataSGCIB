package fr.soat.mi.katasgcib.domain.usecase;

import fr.soat.mi.katasgcib.domain.exception.NotFoundAccountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class HistoryTest {

    private History history;

    @BeforeEach
    void setup() {
        history = new History(new FakeAccountRepository(), new FakeHistoryRepository());
    }

    @Test
    void when_account_name_is_not_in_history_should_throw_exception() {
        assertThatThrownBy(() -> history.execute("unknown account"))
                .isExactlyInstanceOf(NotFoundAccountException.class)
                .hasMessage("account unknown account not found");
    }

    @Test
    void should_get_history_of_account_name() throws IOException, NotFoundAccountException {
        String userHistory = history.execute("jane");
        assertThat(userHistory).contains("withdraw", "14", "20,2");
    }
}