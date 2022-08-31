package de.hsba.bi.grp3.form;

import de.hsba.bi.grp3.service.UserService;
import de.hsba.bi.grp3.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserValidationFormConverter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserValidationFormConverter(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public User userValidationFormToUser (UserValidationForm userValidationForm) {

        String username = userValidationForm.getName();
        String password = passwordEncoder.encode(userValidationForm.getPasswordFirst());

       return new User(username, password);
    }
}
