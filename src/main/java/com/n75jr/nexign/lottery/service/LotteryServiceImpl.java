package com.n75jr.nexign.lottery.service;

import com.n75jr.nexign.lottery.domain.WinnerRecord;
import com.n75jr.nexign.lottery.exception.NotEnoughParticipantsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class LotteryServiceImpl implements LotteryService {

    private final ParticipantService participantService;
    private final RandomService randomService;
    private final WinnerRecordService winnerRecordService;

    @Override
    @Transactional
    public WinnerRecord startLottery() {
        var ids = participantService.findAllId();
        if (log.isTraceEnabled()) {
            log.trace("Starting the lottery with {} participants", ids.size());
        }
        if (ids.size() < 2) {
            if (log.isDebugEnabled()) {
                log.debug("Throw NotEnoughParticipantsException: size = {}", ids.size());
            }
            throw new NotEnoughParticipantsException();
        }
        var winner = detectWinner(ids);
        winnerRecordService.save(winner);
        return winner;
    }

    private WinnerRecord detectWinner(List<Long> ids) {
        var randIndex = randomService.getSingleIntegerRandom(1, ids.size());
        var randSum = randomService.getSingleIntegerRandom(1, 1000);
        var winner = participantService.findById(ids.get(randIndex));
        if (log.isTraceEnabled()) {
            log.trace("Detect winner: participantId = {}, sum = {}, randIndex = {}",
                    winner.getId(), randSum, randIndex);
        }
        return WinnerRecord.of(null, randSum, winner, ZonedDateTime.now());
    }
}
