package de.hsba.bi.grp3.service;

import de.hsba.bi.grp3.repository.UserRepository;
import de.hsba.bi.grp3.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findCurrentUser() {
        return userRepository.findByName(User.getCurrentUsername());
    }
}
