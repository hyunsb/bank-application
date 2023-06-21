package bankApplication.dto;

import bankApplication.model.Account;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountResponse {

    private String number;
    private Long balance;

    public static AccountResponse from(Account account) {
        return new AccountResponse(account.getNumber(), account.getBalance());
    }
}
