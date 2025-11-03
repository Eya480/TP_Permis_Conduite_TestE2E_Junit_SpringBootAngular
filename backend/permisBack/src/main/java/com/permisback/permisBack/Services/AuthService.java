package com.permisback.permisBack.Services;

import com.permisback.permisBack.Entities.User;
import com.permisback.permisBack.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private static final int MAX_ATTEMPTS = 3;
    private static final int LOCK_TIME_MINUTES = 30;

    public AuthResponse authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (!userOpt.isPresent()) {
            return new AuthResponse(false, "Utilisateur non trouvé", false);
        }

        User user = userOpt.get();

        // Vérifier si le compte est verrouillé
        if (user.isLocked()) {
            if (user.getLockTime().plusMinutes(LOCK_TIME_MINUTES).isAfter(LocalDateTime.now())) {
                return new AuthResponse(false, "Compte verrouillé. Réessayez plus tard.", true);
            } else {
                // Déverrouiller le compte après le délai
                user.setLocked(false);
                user.setFailedAttempts(0);
                userRepository.save(user);
            }
        }

        // Vérifier le mot de passe
        if (user.getPassword().equals(password)) {
            user.setFailedAttempts(0);
            userRepository.save(user);
            return new AuthResponse(true, "Authentification réussie", false);
        } else {
            user.setFailedAttempts(user.getFailedAttempts() + 1);

            if (user.getFailedAttempts() >= MAX_ATTEMPTS) {
                user.setLocked(true);
                user.setLockTime(LocalDateTime.now());
            }

            userRepository.save(user);

            return new AuthResponse(false,
                    "Mot de passe incorrect. Tentatives restantes: " + (MAX_ATTEMPTS - user.getFailedAttempts()),
                    user.getFailedAttempts() >= MAX_ATTEMPTS);
        }
    }

    public static class AuthResponse {
        private boolean success;
        private String message;
        private boolean locked;

        public AuthResponse(boolean success, String message, boolean locked) {
            this.success = success;
            this.message = message;
            this.locked = locked;
        }

        // Getters et Setters
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public boolean isLocked() { return locked; }
        public void setLocked(boolean locked) { this.locked = locked; }
    }
}