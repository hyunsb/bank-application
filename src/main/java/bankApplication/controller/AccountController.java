package bankApplication.controller;

import bankApplication.Service.AccountService;
import bankApplication.dto.AccountCreateRequest;
import bankApplication.dto.AccountResponse;
import bankApplication.model.Account;
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
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final HttpSession session;

    @GetMapping("/account/createForm")
    public String createForm() {
        return "account/createForm";
    }

    @PostMapping("/account/create")
    public String create(@ModelAttribute AccountCreateRequest request) {
        log.info("계좌 생성 호출: " + request.toString());
        User user = validateAndGetSessionPrincipal();

        accountService.createAccount(request, user);

        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/account/list")
    public ResponseEntity<?> accountList() {
        User user = validateAndGetSessionPrincipal();

        List<Account> accounts = accountService.findAll(user);
        List<AccountResponse> response = accounts.stream()
                .map(AccountResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    private User validateAndGetSessionPrincipal() {
        if (Objects.isNull(session.getAttribute("principal")))
            throw new IllegalArgumentException("[계좌 생성 오류] 잘못된 접근입니다.");
        return (User) session.getAttribute("principal");
    }
}
