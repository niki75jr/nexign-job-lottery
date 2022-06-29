package com.n75jr.nexign.lottery.controller;

import com.n75jr.nexign.lottery.domain.Participant;
import com.n75jr.nexign.lottery.i18n.Translator;
import com.n75jr.nexign.lottery.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Log4j2
@Validated
@RestController
@RequiredArgsConstructor
public class ParticipantController {

    private final Translator translator;
    private final ParticipantService participantService;

    @GetMapping("/lottery/participant")
    public ResponseEntity<Map<String, ?>> getAllParticipants() {
        if (log.isTraceEnabled()) {
            log.trace("[GET]:/lottery/participant");
        }
        var participants = participantService.findAll();
        return ResponseEntity.ok(Map.of(
                "numberParticipants", participants.size(),
                "participants", participants
        ));
    }

    @PostMapping("/lottery/participant")
    public ResponseEntity<Map<String, ?>> saveParticipant(@RequestBody @Valid Participant participant) {
        if (log.isTraceEnabled()) {
            log.trace("[POST|RB]:/lottery/participant (participant: {})", participant);
        }
        var savedParticipant = participantService.save(participant);
        return ResponseEntity.ok(Map.of(
                "message", translator.toLocale("pcpController.message.successSaved"),
                "id", savedParticipant.getId(),
                "participant", savedParticipant
        ));
    }

    @PostMapping("/lottery/participant/batch")
    public ResponseEntity<Map<String, ?>> saveParticipants(@RequestBody @Valid List<Participant> participants) {
        if (log.isTraceEnabled()) {
            log.trace("[POST|RB]:/lottery/participant/batch (participantsSize: {})", participants.size());
        }
        var savedParticipant = participantService.saveAll(participants);
        var savedIds = savedParticipant.stream()
                .map(Participant::getId)
                .collect(toList());
        return ResponseEntity.ok(Map.of(
                "message", translator.toLocale("pcpController.message.successSaved"),
                "numberParticipants", savedParticipant.size(),
                "id", savedIds
        ));
    }

    @GetMapping("/lottery/participant/generate/{size}")
    public ResponseEntity<Map<String, ?>> generateParticipants(
            @PathVariable(name = "size") @Min(1) @Max(100) int size
    ) {
        if (log.isTraceEnabled()) {
            log.trace("[GET|PV]:/lottery/participant/size[{}]", size);
        }
        var participants = participantService.generate(size);
        return ResponseEntity.ok(Map.of(
                "size", participants.size(),
                "participant", participants
        ));
    }
}

