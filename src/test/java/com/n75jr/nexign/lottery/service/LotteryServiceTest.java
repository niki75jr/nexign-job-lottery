package com.n75jr.nexign.lottery.service;

import com.n75jr.nexign.lottery.domain.Participant;
import com.n75jr.nexign.lottery.exception.NotEnoughParticipantsException;
import com.n75jr.nexign.lottery.util.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

@DisplayName("LotteryService test")
@SpringBootTest(classes = LotteryServiceImpl.class)
class LotteryServiceTest {

    private static List<Participant> notEnoughParticipants;
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
        notEnoughParticipants = Utils.generateParticipants(1);
        participants = Utils.generateParticipants(10);
    }

    @Test
    void shouldThrowExceptionBecauseOfNotEnoughParticipants() {
        doReturn(notEnoughParticipants.stream()
                        .map(Participant::getId)
                        .collect(toList()))
                .when(participantService).findAllId();

        assertThrows(NotEnoughParticipantsException.class,
                () -> lotteryService.startLottery());
    }

    @Test
    void shouldReturnWinnerRecord() {
        doReturn(participants.stream()
                        .map(Participant::getId)
                        .collect(toList()))
                .when(participantService).findAllId();
        var participantsSize = participants.size();
        var randomIndex = ThreadLocalRandom.current().nextInt(participantsSize);
        var randomSum = ThreadLocalRandom.current().nextInt(1000) + 1;
        var winnerId = participants.get(randomIndex).getId();
        doReturn(randomIndex, randomSum)
                .when(randomService).getSingleIntegerRandom(anyInt(), anyInt());
        doReturn(participants.get(randomIndex))
                .when(participantService).findById(winnerId);
        var winner = participants.get(randomIndex);
        var winnerRecord = lotteryService.startLottery();

        assertAll(
                () -> assertThat(winnerRecord.getSum()).isEqualTo(randomSum),
                () -> assertThat(winnerRecord.getParticipant()).isEqualTo(winner)
        );
    }
}