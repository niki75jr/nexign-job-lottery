package com.n75jr.nexign.lottery.controller;

import com.n75jr.nexign.lottery.i18n.Translator;
import com.n75jr.nexign.lottery.service.LotteryService;
import com.n75jr.nexign.lottery.service.WinnerRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
public class LotteryController {

    private final Translator translator;
    private final LotteryService lotteryService;
    private final WinnerRecordService winnerRecordService;

    @GetMapping("/lottery/start")
    public ResponseEntity<Map<String, ?>> startLottery() {
        if (log.isTraceEnabled()) {
            log.trace("[GET]:/lottery/start");
        }
        var winnerDto = lotteryService.startLottery();
        return ResponseEntity.ok(Map.of(
                "sum", winnerDto.getSum(),
                "winner", winnerDto.getParticipant()
        ));
    }

    @GetMapping("/lottery/winners")
    public ResponseEntity<Map<String, ?>> getAllWinnerRecords() {
        if (log.isTraceEnabled()) {
            log.trace("[GET]:/lottery/winners");
        }
        var winnerRecords = winnerRecordService.findAllWithWinners();
        if (winnerRecords.size() == 0) {
            return ResponseEntity.ok(Map.of(
                            "message", translator.toLocale("lotController.message.status.noContent")
                    ));
        }
        return ResponseEntity.ok(Map.of(
                "size", winnerRecords.size(),
                "winners", winnerRecords
        ));
    }
}
