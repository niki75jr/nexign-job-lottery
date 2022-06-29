package com.n75jr.nexign.lottery.repository;

import com.n75jr.nexign.lottery.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("select p.id from Participant p")
    List<Long> findAllId();
}
