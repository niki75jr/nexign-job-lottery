package com.n75jr.nexign.lottery.shell;

import com.n75jr.nexign.lottery.domain.WinnerRecord;
import com.n75jr.nexign.lottery.exception.NotEnoughParticipantsException;
import com.n75jr.nexign.lottery.service.IOService;
import com.n75jr.nexign.lottery.service.LotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@ShellCommandGroup("Lottery")
@RequiredArgsConstructor
public class LotteryShell {

    private final IOService iOservice;
    private final LotteryService lotteryService;

    @ShellMethod(value = "Start a lottery", key = "lotteryStart")
    public void startLottery() {
        iOservice.println("Starting the lottery...");
        WinnerRecord winnerRecord;
        try {
            winnerRecord = lotteryService.startLottery();
        } catch (NotEnoughParticipantsException ex) {
            iOservice.println("Not enough some participants");
            return;
        }
        var winner = winnerRecord.getParticipant();
        System.out.println(winnerRecord.getSum() + " wins "
                + winner.getLastName() + " " + winner.getLastName() +
                " from " + winner.getCity());
    }
}
