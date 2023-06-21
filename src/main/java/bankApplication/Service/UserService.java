package bankApplication.Service;

import bankApplication.dto.UserJoinRequest;
import bankApplication.model.User;
import bankApplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User join(UserJoinRequest userJoinRequest) {
        validateJoinRequest(userJoinRequest);
        User user = UserJoinRequest.toEntity(userJoinRequest);
        return userRepository.save(user);
    }

    private void validateJoinRequest(UserJoinRequest userJoinRequest) throws IllegalArgumentException {
        if (Objects.isNull(userJoinRequest.getEmail()))
            throw new IllegalArgumentException("[회원가입 실패]: email이 존재하지 않습니다.");
        if (Objects.isNull(userJoinRequest.getPassword()))
            throw new IllegalArgumentException("[회원가입 실패]: password가 존재하지 않습니다.");
        if (Objects.isNull(userJoinRequest.getUsername()))
            throw new IllegalArgumentException("[회원가입 실패]: username이 존재하지 않습니다.");
        validateDuplicateEmail(userJoinRequest.getEmail());
    }

    private void validateDuplicateEmail(String email) throws IllegalArgumentException {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new IllegalArgumentException("[회원가입 실패]: 중복된 이메일 입니다.");
        });
    }
}
