package com.example.registration.appuser;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.registration.registration.token.ConfirmationToken;
import com.example.registration.registration.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;

@Service
@Component
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE = "User with username %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));
    }

    @Transactional
    public String signUpUser(AppUser user) {
        boolean userExists = appUserRepository.findUserByEmail(user.getEmail()).isPresent();
        if (userExists) {
            return "email already exists";
        }
        userExists = appUserRepository.findUserByUsername(user.getUsername()).isPresent();
        if (userExists) {
            return "username already exists";
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(user.getPassword());
        user.setPassword(encodedPassword);

        appUserRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableAppUser(String email){
        return appUserRepository.enableAppUser(email);
    }
}
