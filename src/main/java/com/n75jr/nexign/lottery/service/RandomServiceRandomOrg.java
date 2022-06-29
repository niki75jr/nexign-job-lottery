package com.n75jr.nexign.lottery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class RandomServiceRandomOrg implements RandomService {

    public static final String URL_RANDOM_ORG =
            "https://www.random.org/integers/?num={num}&min={min}&max={max}&col=1&base=10&format=plain&rnd=new";

    private final RestTemplate restTemplate;

    @Override
    public int getSingleIntegerRandom(int min, int max) {
        var randInt = Integer.valueOf(
                restTemplate.getForObject(URL_RANDOM_ORG,
                        String.class,
                        Map.of(
                                "num", 1,
                                "min", min,
                                "max", max
                        )).trim());
        if (log.isTraceEnabled()) {
            log.trace("Received from random.org: {}", randInt);
        }
        return randInt;
    }
}
