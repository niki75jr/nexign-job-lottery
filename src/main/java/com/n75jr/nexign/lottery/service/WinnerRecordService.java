package com.n75jr.nexign.lottery.service;

import com.n75jr.nexign.lottery.domain.WinnerRecord;

import java.util.List;

public interface WinnerRecordService {

    long count();
    WinnerRecord save(WinnerRecord winnerRecord);

    List<WinnerRecord> findAllWithWinners();
}
