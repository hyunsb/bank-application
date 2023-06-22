package bankApplication.dto;

import bankApplication.model.Account;
import bankApplication.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountCreateRequest {

    private String number;
    private String password;

    public static Account toEntity(AccountCreateRequest accountCreateRequest, User user) {
        return Account.builder()
                .number(accountCreateRequest.getNumber())
                .password(accountCreateRequest.getPassword())
                .user(user)
                .build();
    }
}
