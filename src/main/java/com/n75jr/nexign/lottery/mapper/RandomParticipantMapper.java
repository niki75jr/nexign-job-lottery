package com.n75jr.nexign.lottery.mapper;

import com.n75jr.nexign.lottery.domain.Participant;
import com.n75jr.nexign.lottery.dto.RandomParticipantDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class RandomParticipantMapper {

    public Participant toParticipant(RandomParticipantDTO dto) {
        if (log.isTraceEnabled()) {
            log.trace("Mapping DTO to participant");
        }
        return Participant.of(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getAge(),
                dto.getCity()
        );
    }

    public RandomParticipantDTO toRandomParticipantDTO(Participant participant) {
        if (log.isTraceEnabled()) {
            log.trace("Mapping participant to DTO");
        }
        return RandomParticipantDTO.of(
                participant.getId(),
                participant.getFirstName(),
                participant.getLastName(),
                participant.getAge(),
                participant.getCity()
        );
    }
}
