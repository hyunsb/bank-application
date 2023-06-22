package bankApplication.repository;

import bankApplication.model.Account;
import bankApplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByNumber(String number);

    List<Account> findByUser(User user);
}
