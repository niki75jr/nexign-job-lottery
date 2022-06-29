package com.n75jr.nexign.lottery.service;

import com.n75jr.nexign.lottery.domain.Participant;
import com.n75jr.nexign.lottery.dto.RandomParticipantDTO;
import com.n75jr.nexign.lottery.exception.EntityNotFound;
import com.n75jr.nexign.lottery.mapper.RandomParticipantMapper;
import com.n75jr.nexign.lottery.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.toList;

@Log4j2
@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final RestTemplate restTemplate;
    private final ParticipantRepository participantRepo;
    private final RandomParticipantMapper randomParticipantMapper;

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return participantRepo.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> findAllId() {
        return participantRepo.findAllId();
    }

    @Override
    @Transactional
    public Participant save(Participant participant) {
        return participantRepo.save(participant);
    }

    @Override
    @Transactional
    public List<Participant> saveAll(Iterable<Participant> participants) {
        return participantRepo.saveAll(participants);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Participant> findAll() {
        return participantRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Participant findById(Long id) {
        return participantRepo.findById(id)
                .orElseThrow(() -> new EntityNotFound());
    }

    @Override
    @Transactional
    public List<Participant> generate(int size) {
        var uri = "https://random-data-api.com/api/users/random_user?size={size}";
        var participantsDTO = restTemplate.exchange(
                RequestEntity.get(uri, size).build(),
                new ParameterizedTypeReference<List<RandomParticipantDTO>>() {
                }
        ).getBody();
        participantsDTO.stream().forEach(this::setRandomAge);
        var participants = participantsDTO.stream()
                .map(randomParticipantMapper::toParticipant)
                .collect(toList());
        if (log.isTraceEnabled()) {
            log.trace("Generated {} participants", participants.size());
        }
        var savedParticipants = participantRepo.saveAll(participants);
        if (log.isTraceEnabled()) {
            log.trace("Saved {} participants", savedParticipants.size());
        }
        return participants;
    }

    private void setRandomAge(RandomParticipantDTO dto) {
        var age = ThreadLocalRandom.current().nextInt(100) + 18;
        dto.setAge(age);
    }
}
