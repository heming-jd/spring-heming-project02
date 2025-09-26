package org.example.springhemingproject02.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springhemingproject02.dox.User;
import org.example.springhemingproject02.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InitService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @EventListener(classes = ApplicationReadyEvent.class)
    public void init() {
        String account = "admin";
        long count = userRepository.count();
        if (count > 0) {
            return;
        }
        User user = User.builder()
                .account(account)
                .password(passwordEncoder.encode(account))
                .role(User.ROLE_ADMIN)
                .build();
        userRepository.save(user);
    }
}
