package bankApplication.dto;

import bankApplication.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {

    private String email;
    private String username;

    public static UserResponse from(User user) {
        return new UserResponse(user.getEmail(), user.getUsername());
    }
}
