package com.n75jr.nexign.lottery.service;

import com.n75jr.nexign.lottery.domain.Participant;
import com.n75jr.nexign.lottery.mapper.RandomParticipantMapper;
import com.n75jr.nexign.lottery.repository.ParticipantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validator;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ParticipantService test")
@SpringBootTest(classes = {ParticipantServiceImpl.class, LocalValidatorFactoryBean.class})
class ParticipantServiceTest {

    private static final int EXPECTED_SIZE_GENERATED_PARTICIPANTS = 10;

    @Autowired
    private ParticipantService participantService;
    @Autowired
    private Validator validator;
    @MockBean
    private ParticipantRepository participantRepository;
    @SpyBean
    private RandomParticipantMapper randomParticipantMapper;
    @SpyBean
    private RestTemplate restTemplate;

    @Test
    void shouldReturnExpectedSizeOfParticipants() {
        var participants = participantService.generate(EXPECTED_SIZE_GENERATED_PARTICIPANTS);
        assertThat(participants).hasSize(EXPECTED_SIZE_GENERATED_PARTICIPANTS);
    }

    @Test
    void shouldReturnValidParticipants() {
        var participants = participantService.generate(EXPECTED_SIZE_GENERATED_PARTICIPANTS);
        assertThat(participants).filteredOn(this::isValid);
    }

    boolean isValid(Participant participant) {
        return validator.validate(participant).isEmpty();
    }
}