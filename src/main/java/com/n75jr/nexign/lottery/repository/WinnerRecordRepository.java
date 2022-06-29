package com.n75jr.nexign.lottery.repository;

import com.n75jr.nexign.lottery.domain.WinnerRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WinnerRecordRepository extends JpaRepository<WinnerRecord, Long> {

    @Query("select wr from WinnerRecord wr join fetch wr.participant")
    List<WinnerRecord> findAllWithParticipant();
}
