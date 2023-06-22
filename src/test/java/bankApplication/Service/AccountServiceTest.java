package bankApplication.Service;

import bankApplication.dto.AccountCreateRequest;
import bankApplication.model.Account;
import bankApplication.model.User;
import bankApplication.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @DisplayName("계좌 생성 성공 테스트")
    @Test
    void create_Success_Test() {
        // Given
        Mockito.when(accountRepository.save(ArgumentMatchers.any(Account.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        User user = User.builder().email("1234").password("1234").username("testUser").build();
        AccountCreateRequest expected = new AccountCreateRequest("1111", "1111");

        // When
        Account actual = accountService.createAccount(expected, user);

        // Then
        Assertions.assertEquals(expected.getNumber(), actual.getNumber());
        Assertions.assertEquals(expected.getPassword(), actual.getPassword());
    }

    @DisplayName("계좌 생성 실패 테스트 - 중복 계좌")
    @Test
    void create_Failed_Test_DuplicateAccountNumber() {
        // Given
        Mockito.when(accountRepository.save(ArgumentMatchers.any(Account.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        User user = User.builder().email("1234").password("1234").username("testUser").build();
        AccountCreateRequest firstRequest = new AccountCreateRequest("1111", "1111");
        Account account = accountService.createAccount(firstRequest, user);

        Mockito.when(accountRepository.findByNumber(ArgumentMatchers.any(String.class)))
                .thenReturn(Optional.of(account));

        // When
        AccountCreateRequest secondRequest = new AccountCreateRequest("1111", "1111");

        // Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            accountService.createAccount(secondRequest, user);
        });
    }
}