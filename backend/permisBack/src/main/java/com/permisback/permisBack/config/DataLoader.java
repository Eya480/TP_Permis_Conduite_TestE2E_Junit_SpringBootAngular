package com.permisback.permisBack.config;

import com.permisback.permisBack.Entities.User;
import com.permisback.permisBack.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("user123").isEmpty()) {
            User user = new User("user123", "password123");
            userRepository.save(user);
        }
    }
}
