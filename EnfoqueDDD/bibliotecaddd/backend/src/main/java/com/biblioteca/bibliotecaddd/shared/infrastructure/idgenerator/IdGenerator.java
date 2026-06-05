package com.biblioteca.bibliotecaddd.shared.infrastructure.idgenerator;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class IdGenerator {
    public String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public String generatePrefixed(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8);
    }
}