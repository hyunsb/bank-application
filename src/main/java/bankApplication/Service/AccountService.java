package bankApplication.Service;

import bankApplication.dto.AccountCreateRequest;
import bankApplication.model.Account;
import bankApplication.model.User;
import bankApplication.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account createAccount(AccountCreateRequest accountCreateRequest, User user) {
        validateAccountRequest(accountCreateRequest);
        Account account = AccountCreateRequest.toEntity(accountCreateRequest, user);

        return accountRepository.save(account);
    }

    private void validateAccountRequest(AccountCreateRequest accountCreateRequest) throws IllegalArgumentException {
        if (Objects.isNull(accountCreateRequest.getNumber())) throw new IllegalArgumentException("[계좌 생성 실패] 계좌 번호가 존재하지 않습니다.");
        if (Objects.isNull(accountCreateRequest.getPassword())) throw new IllegalArgumentException("[계좌 생성 실패] 비밀 번호가 존재하지 않습니다.");
        duplicateAccountNumber(accountCreateRequest.getNumber());
    }

    private void duplicateAccountNumber(String accountNumber) throws IllegalArgumentException {
        if (accountRepository.findByNumber(accountNumber).isPresent())
            throw new IllegalArgumentException("[계좌 생성 실패] 중복되는 계좌번호 입니다.");
    }

    public List<Account> findAll(User user) {
        List<Account> accounts = accountRepository.findByUser(user);
        return accounts;
    }
}
