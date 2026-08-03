package com.example.spring_rest.security;

import com.example.spring_rest.model.OneTimeToken;
import com.example.spring_rest.repository.OneTimeTokenRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class MfaService {

    private final OneTimeTokenRepository oneTimeTokenRepository;
    private static final SecureRandom secureRandom = new SecureRandom();

    public MfaService(OneTimeTokenRepository oneTimeTokenRepository) {
        this.oneTimeTokenRepository = oneTimeTokenRepository;
    }

    public String generateCode(String email) {
        String code = String.valueOf(100000 + secureRandom.nextInt(900000));

        OneTimeToken ott = new OneTimeToken();
        ott.setEmail(email);
        ott.setCode(code);
        ott.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        ott.setUsed(false);

        oneTimeTokenRepository.save(ott);

        return code;
    }

    public boolean verifyCode(String email, String code) {
        return oneTimeTokenRepository.findByEmailAndCodeAndUsedFalse(email, code)
            .filter(ott -> ott.getExpiresAt().isAfter(LocalDateTime.now()))
            .map(ott -> {
                ott.setUsed(true);
                oneTimeTokenRepository.save(ott);
                return true;
            })
            .orElse(false);
    }
}