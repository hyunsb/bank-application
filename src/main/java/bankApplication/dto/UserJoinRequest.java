package bankApplication.dto;

import bankApplication.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserJoinRequest {

    private String email;
    private String password;
    private String username;

    public static User toEntity(UserJoinRequest userJoinRequest) {
        return User.builder()
                .email(userJoinRequest.getEmail())
                .password(userJoinRequest.password)
                .username(userJoinRequest.username)
                .build();
    }
}
