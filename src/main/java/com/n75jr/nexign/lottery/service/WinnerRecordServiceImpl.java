package com.n75jr.nexign.lottery.service;

import com.n75jr.nexign.lottery.domain.WinnerRecord;
import com.n75jr.nexign.lottery.repository.WinnerRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WinnerRecordServiceImpl implements WinnerRecordService {

    private final WinnerRecordRepository winnerRecordRepo;

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return winnerRecordRepo.count();
    }

    @Override
    @Transactional
    public WinnerRecord save(WinnerRecord winnerRecord) {
        return winnerRecordRepo.save(winnerRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WinnerRecord> findAllWithWinners() {
        return winnerRecordRepo.findAllWithParticipant();
    }
}
