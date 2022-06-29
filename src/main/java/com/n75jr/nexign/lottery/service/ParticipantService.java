package com.n75jr.nexign.lottery.service;

import com.n75jr.nexign.lottery.domain.Participant;

import java.util.List;

public interface ParticipantService {

    long count();
    List<Long> findAllId();
    Participant save(Participant participant);
    List<Participant> saveAll(Iterable<Participant> participants);
    List<Participant> findAll();
    Participant findById(Long id);
    List<Participant> generate(int size);
}
