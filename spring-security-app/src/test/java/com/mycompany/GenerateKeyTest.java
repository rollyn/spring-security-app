package com.mycompany;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Base64;

public class GenerateKeyTest {

    @Test
    public void generateKey() {
            SecureRandom random = new SecureRandom();
            byte[] keyBytes = new byte[32];
            random.nextBytes(keyBytes);

            String secretKey = Base64.getEncoder().encodeToString(keyBytes);
            System.out.println("KEY: " + secretKey);
    }
}
