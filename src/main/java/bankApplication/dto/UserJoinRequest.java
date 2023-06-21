package bankApplication.dto;

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
}
