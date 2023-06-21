package bankApplication.Service;

import bankApplication.dto.UserJoinRequest;
import bankApplication.dto.UserLoginRequest;
import bankApplication.model.User;
import bankApplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User join(UserJoinRequest userJoinRequest) {
        validateJoinRequest(userJoinRequest);
        User user = UserJoinRequest.toEntity(userJoinRequest);
        return userRepository.save(user);
    }

    private void validateJoinRequest(UserJoinRequest request) throws IllegalArgumentException {
        if (Objects.isNull(request.getEmail()))
            throw new IllegalArgumentException("[회원가입 실패]: email이 존재하지 않습니다.");
        if (Objects.isNull(request.getPassword()))
            throw new IllegalArgumentException("[회원가입 실패]: password가 존재하지 않습니다.");
        if (Objects.isNull(request.getUsername()))
            throw new IllegalArgumentException("[회원가입 실패]: username이 존재하지 않습니다.");
        validateDuplicateEmail(request.getEmail());
    }

    private void validateDuplicateEmail(String email) throws IllegalArgumentException {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new IllegalArgumentException("[회원가입 실패]: 중복된 이메일 입니다.");
        });
    }

    public User login(UserLoginRequest userLoginRequest) {
        validateLoginRequest(userLoginRequest);
        return userRepository.findByEmailAndPassword(userLoginRequest.getEmail(), userLoginRequest.getPassword())
                .orElseThrow(() -> new IllegalArgumentException("[로그인 실패]: 일치하는 정보가 존재하지 않습니다."));
    }

    private void validateLoginRequest(UserLoginRequest request) throws IllegalArgumentException {
        if (Objects.isNull(request.getEmail()))
            throw new IllegalArgumentException("[회원가입 실패]: email이 존재하지 않습니다.");
        if (Objects.isNull(request.getPassword()))
            throw new IllegalArgumentException("[회원가입 실패]: password가 존재하지 않습니다.");
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }
}
