package atraintegratedsystems.service;

import atraintegratedsystems.model.CustomUserDetail;
import atraintegratedsystems.model.User;
import atraintegratedsystems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user= userRepository.findByEmail(email);
        user.orElseThrow(()->new UsernameNotFoundException("User is not Exist"));
        return user.map(CustomUserDetail::new).get();

    }
}
