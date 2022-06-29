package com.n75jr.nexign.lottery.service;

import com.n75jr.nexign.lottery.domain.Participant;
import com.n75jr.nexign.lottery.exception.NotEnoughParticipantsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("LotteryService test")
@SpringBootTest(classes = LotteryServiceImpl.class)
class LotteryServiceTest {

    private final static List<Long> NOT_ENOUGH_ID_PARTICIPANTS = List.of(1L);

    private static List<Participant> participants;

    @Autowired
    private LotteryService lotteryService;
    @MockBean
    private ParticipantService participantService;
    @MockBean
    private RandomService randomService;
    @MockBean
    private WinnerRecordService winnerRecordService;

    @BeforeAll
    static void setUp() {
        participants = List.of(
                Participant.of(1L, "Ivan", "Ivanov", 18, "Vladivostok"),
                Participant.of(2L, "Petr", "Petrov", 20, "Chita"),
                Participant.of(3L, "Anna", "Zolotoryova", 23, "Irkutsk")
        );
    }

    @Test
    void shouldThrowExceptionBecauseOfNotEnoughParticipants() {
        Mockito.doReturn(NOT_ENOUGH_ID_PARTICIPANTS)
                .when(participantService).findAllId();
        Assertions.assertThrows(NotEnoughParticipantsException.class, () -> lotteryService.startLottery());
    }

    @Test
    void shouldReturnWinnerRecord() {
        Mockito.doReturn(participants.stream()
                        .map(Participant::getId)
                        .collect(Collectors.toList()))
                .when(participantService).findAllId();
        var participantsSize = participants.size();
        var randomIndex = ThreadLocalRandom.current().nextInt(participantsSize);
        var randomSum = ThreadLocalRandom.current().nextInt(1000) + 1;
        var winnerId = participants.get(randomIndex).getId();
        Mockito.doReturn(randomIndex).when(randomService).getSingleIntegerRandom(1, participantsSize);
        Mockito.doReturn(randomSum).when(randomService).getSingleIntegerRandom(1, 1000);
        Mockito.doReturn(participants.get(randomIndex)).when(participantService).findById(winnerId);
        var winner = participants.get(randomIndex);
        var winnerRecord = lotteryService.startLottery();

        assertAll(
                () -> assertThat(winnerRecord.getSum()).isEqualTo(randomSum),
                () -> assertThat(winnerRecord.getParticipant()).isEqualTo(winner)
        );
    }
}