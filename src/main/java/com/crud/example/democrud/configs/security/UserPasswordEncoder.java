package com.crud.example.democrud.configs.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Component
public class UserPasswordEncoder implements PasswordEncoder {

    @Value("${application.password.encoder.secretSaltKey}")
    private String secretSaltKey;

    @Value("${application.password.encoder.iteration}")
    private Integer iteration;

    @Value("${application.password.encoder.keylength}")
    private Integer keyLength;

    @Override
    public String encode(CharSequence rawPassword) {
        try {

            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(
                            rawPassword.toString().toCharArray(),
                            secretSaltKey.getBytes(),
                            iteration,
                            keyLength
                    ))
                    .getEncoded();

            return Base64.getEncoder().encodeToString(result);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException("failed encode password", ex);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
