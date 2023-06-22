package bankApplication.controller;

import bankApplication.Service.AccountService;
import bankApplication.dto.AccountCreateRequest;
import bankApplication.model.Account;
import bankApplication.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    private Account expected;

    @BeforeEach
    void setUp() {
        expected = Account.builder()
                .id(123L)
                .number("1111")
                .password("1111")
                .balance(1000L)
                .build();
    }

    @DisplayName("계좌 생성 성공 테스트")
    @Test
    void create_Success_Test() throws Exception {
        Mockito.when(accountService.createAccount(
                        ArgumentMatchers.any(AccountCreateRequest.class),
                        ArgumentMatchers.any(User.class)))
                .then(invocation -> {
                    AccountCreateRequest request = invocation.getArgument(0, AccountCreateRequest.class);
                    return Account.builder()
                            .id(expected.getId())
                            .number(request.getNumber())
                            .password(request.getPassword())
                            .balance(expected.getBalance())
                            .build();
                });

        HttpSession session = new MockHttpSession();
        session.setAttribute("principal", User.builder().id(1L).email("1111").username("1111"));

        AccountCreateRequest request = new AccountCreateRequest();
        request.setNumber("1111");
        request.setPassword("1111");

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        MultiValueMap<String, String> formData = mapper.readValue(content, LinkedMultiValueMap.class);
        String formUrlEncoded = UriComponentsBuilder.newInstance()
                .queryParams(formData)
                .build()
                .encode()
                .toString();

        mvc.perform(MockMvcRequestBuilders.post("/account/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(formUrlEncoded))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}