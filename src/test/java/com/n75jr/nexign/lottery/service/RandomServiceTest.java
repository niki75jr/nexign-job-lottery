package com.n75jr.nexign.lottery.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testing receiving of a random int from random.org")
@SpringBootTest(classes = RandomServiceRandomOrg.class)
class RandomServiceTest {

    private static final int MIN_BOUND = 1;
    private static final int MAX_BOUND = 100;

    @Autowired
    private RandomService randomService;
    @SpyBean
    private RestTemplate restTemplate;

    @Test
    void shouldGetRandomWithinValidRange() {
        var randomValue = randomService.getSingleIntegerRandom(MIN_BOUND, MAX_BOUND);

        assertThat(randomValue)
                .isGreaterThanOrEqualTo(MIN_BOUND)
                .isLessThanOrEqualTo(MAX_BOUND);
    }
}