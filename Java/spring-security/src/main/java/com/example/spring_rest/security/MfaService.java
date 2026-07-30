package com.example.spring_rest.security;

import com.example.spring_rest.model.OneTimeToken;
import com.example.spring_rest.repository.OneTimeTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class MfaService {

    private final OneTimeTokenRepository oneTimeTokenRepository;

    public MfaService(OneTimeTokenRepository oneTimeTokenRepository) {
        this.oneTimeTokenRepository = oneTimeTokenRepository;
    }

    public String generateCode(String username) {
        String code = String.valueOf(100000 + new Random().nextInt(900000));

        OneTimeToken ott = new OneTimeToken();
        ott.setUsername(username);
        ott.setCode(code);
        ott.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        ott.setUsed(false);

        oneTimeTokenRepository.save(ott);

        return code;
    }

    public boolean verifyCode(String username, String code) {
        return oneTimeTokenRepository.findByUsernameAndCodeAndUsedFalse(username, code)
            .filter(ott -> ott.getExpiresAt().isAfter(LocalDateTime.now()))
            .map(ott -> {
                ott.setUsed(true);
                oneTimeTokenRepository.save(ott);
                return true;
            })
            .orElse(false);
    }
}