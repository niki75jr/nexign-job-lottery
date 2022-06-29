package com.n75jr.nexign.lottery.shell;

import com.n75jr.nexign.lottery.domain.WinnerRecord;
import com.n75jr.nexign.lottery.service.IOService;
import com.n75jr.nexign.lottery.service.WinnerRecordService;
import com.n75jr.nexign.lottery.util.ShellUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@ShellCommandGroup("WinnerRecord")
@RequiredArgsConstructor
public class WinnerRecordShell {

    private final IOService iOservice;
    private final WinnerRecordService winnerRecordService;

    @ShellMethod(value = "Print all winnerRecord from db", key = "winnerRecordAll")
    public void printAllWinnerRecordWithWinners() {
        var num = winnerRecordService.count();

        if (num == 0) {
            iOservice.println("List of winnerRecord is empty");
            return;
        }
        else if (num > 10) {
            iOservice.println("Number of winnerRecords: " + num);
            String answer;
            do {
                iOservice.print("Do you want to display all?: (y/n)> ");
                answer = iOservice.readString();
            } while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));
            if (answer.equalsIgnoreCase("n")) {
                return;
            }
        }

        var winnerRecords = winnerRecordService.findAllWithWinners();
        ShellUtils.printItems(winnerRecords, iOservice, this::toFunctionString);
    }

    private String toFunctionString(WinnerRecord winnerRecord) {
        var participant = winnerRecord.getParticipant();
        StringBuilder sb = new StringBuilder();
        sb.append(winnerRecord)
                .append(System.lineSeparator())
                .append("\t")
                .append(participant);
        return sb.toString();
    }
}

