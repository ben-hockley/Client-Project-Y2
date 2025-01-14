package org.example.groupproject.applicant.security;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class LoginFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final int maxAttempts = 1;
    private final long lockTimeDuration = TimeUnit.MINUTES.toMillis(1);

    private final Map<String, LoginAttempt> attemptsCache = new ConcurrentHashMap<>();

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = event.getAuthentication().getName();
        if (username != null) {
            loginFailed(username);
        }
    }

    public void loginFailed(String username) {
        LoginAttempt attempt = attemptsCache.getOrDefault(username, new LoginAttempt(0, System.currentTimeMillis()));
        attempt.incrementAttempts();
        attemptsCache.put(username, attempt);
    }

    public boolean isBlocked(String username) {
        LoginAttempt attempt = attemptsCache.get(username);
        if (attempt == null) {
            return false;
        }
        if (attempt.getAttempts() >= maxAttempts) {
            long elapsedTime = System.currentTimeMillis() - attempt.getLastAttemptTime();
            if (elapsedTime < lockTimeDuration) {
                return true;
            }
            // Reset block status after the lock duration has elapsed
            attemptsCache.remove(username);
        }
        return false;
    }


    public void loginSucceeded(String username) {
        attemptsCache.remove(username);
    }

    private static class LoginAttempt {
        private int attempts;
        private long lastAttemptTime;

        public LoginAttempt(int attempts, long lastAttemptTime) {
            this.attempts = attempts;
            this.lastAttemptTime = lastAttemptTime;
        }

        public void incrementAttempts() {
            this.attempts++;
            this.lastAttemptTime = System.currentTimeMillis();
        }

        public int getAttempts() {
            return attempts;
        }

        public long getLastAttemptTime() {
            return lastAttemptTime;
        }
    }
}
