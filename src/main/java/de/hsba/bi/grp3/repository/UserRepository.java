package de.hsba.bi.grp3.repository;

import de.hsba.bi.grp3.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Long> {

    User findByName(String name);

    List<User> findByRole(String role);

}
