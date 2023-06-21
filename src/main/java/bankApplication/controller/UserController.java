package bankApplication.controller;

import bankApplication.dto.UserJoinRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class UserController {

    @GetMapping("/user/loginForm")
    public String login() {
        return "/user/loginForm";
    }

    @GetMapping("/user/joinForm")
    public String join() {
        return "/user/joinForm";
    }

    @PostMapping("/user/join")
    public String join(@ModelAttribute UserJoinRequest request) {
        log.info(request.toString());
        return null;
    }
}
