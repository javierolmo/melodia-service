package com.javi.uned.pfgbackend.domain.util;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class UtilService implements UtilConstants {

    public String generateRandomTitle() {
        String randomSustantive = sustantives[ThreadLocalRandom.current().nextInt(0, sustantives.length)];
        String randomAdjective = adjectives[ThreadLocalRandom.current().nextInt(0, adjectives.length)];
        randomSustantive = randomSustantive.substring(0, 1).toUpperCase() + randomSustantive.substring(1);
        return String.format("%s %s", randomSustantive, randomAdjective);
    }

}
