package de.hsba.bi.grp3.service;

import javax.transaction.Transactional;

import de.hsba.bi.grp3.repository.UserRepository;
import de.hsba.bi.grp3.user.User;
import de.hsba.bi.grp3.user.UserAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAdapterService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserAdapter(user);
    }
}
