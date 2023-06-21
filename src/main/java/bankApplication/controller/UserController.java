package bankApplication.controller;

import bankApplication.Service.UserService;
import bankApplication.dto.UserJoinRequest;
import bankApplication.dto.UserLoginRequest;
import bankApplication.dto.UserResponse;
import bankApplication.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final HttpSession session;

    @GetMapping("/user/loginForm")
    public String login() {
        return "/user/loginForm";
    }

    @GetMapping("/user/joinForm")
    public String join() {
        return "/user/joinForm";
    }

    @ResponseBody
    @PostMapping("/user/join")
    public ResponseEntity<UserResponse> join(@ModelAttribute UserJoinRequest request) {
        log.info("join Request: " + request.toString());

        User user = userService.join(request);
        UserResponse response = new UserResponse(user.getEmail(), user.getUsername());

        return ResponseEntity.ok(response);
//        return "redirect:/user/joinForm";
    }

    @ResponseBody
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@ModelAttribute UserLoginRequest request) {
        log.info("login Request: " + request.toString());

        User principal = userService.login(request);
        session.setAttribute("principal", principal);

        UserResponse response = UserResponse.from(principal);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/list")
    public ResponseEntity<?> userList() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }
}
