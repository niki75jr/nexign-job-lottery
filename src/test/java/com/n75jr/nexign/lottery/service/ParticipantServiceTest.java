package com.n75jr.nexign.lottery.service;

import com.n75jr.nexign.lottery.domain.Participant;
import com.n75jr.nexign.lottery.mapper.RandomParticipantMapper;
import com.n75jr.nexign.lottery.repository.ParticipantRepository;
import com.n75jr.nexign.lottery.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validator;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@DisplayName("ParticipantService test")
@SpringBootTest(classes = {ParticipantServiceImpl.class, LocalValidatorFactoryBean.class})
class ParticipantServiceTest {

    private static final int EXPECTED_SIZE_GENERATED_PARTICIPANTS = 10;

    @Autowired
    private ParticipantService participantService;
    @Autowired
    private Validator validator;
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private ParticipantRepository participantRepository;

    @BeforeEach
    void setUp() {
        var genParticipants = new ResponseEntity<>(Utils.
                generateParticipants(EXPECTED_SIZE_GENERATED_PARTICIPANTS, false).stream()
                .map(RandomParticipantMapper::toRandomParticipantDTO)
                .collect(toList()), HttpStatus.OK);
        doReturn(genParticipants)
                .when(restTemplate).exchange(any(RequestEntity.class),
                        any(ParameterizedTypeReference.class));
    }

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