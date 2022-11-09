package hexlet.code.service.impl;

import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("USER"));
        User user = repository.findByEmail(email).get();
        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), authorities);

    }

}
