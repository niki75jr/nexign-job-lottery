package com.n75jr.nexign.lottery.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n75jr.nexign.lottery.domain.Participant;
import com.n75jr.nexign.lottery.i18n.Translator;
import com.n75jr.nexign.lottery.service.ParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ParticipantController test")
@WebMvcTest(ParticipantController.class)
class ParticipantControllerTest {

    private static final String URI_GET_ALL = "/lottery/participant";
    private static final String URI_ADD = "/lottery/participant";
    private static final Long EXPECTED_NEW_ID = 1L;
    private List<Participant> participants;
    private Participant participantForSaving;
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Translator translator;
    @MockBean
    private ParticipantService participantService;

    @BeforeEach
    void setUp() {
        participants = List.of(
                Participant.of(1L, "Ivan", "Ivanov", 18, "Vladivostok"),
                Participant.of(2L, "Petr", "Petrov", 20, "Chita"),
                Participant.of(3L, "Anna", "Zolotoryova", 23, "Irkutsk")
        );
        participantForSaving = Participant.of(null, "Ilya", "Romanov", 21, "SpB");
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturnAllParticipants() throws Exception {
        doReturn(participants).when(participantService).findAll();
        var rawResponse = mockMvc.perform(get(URI_GET_ALL))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var participantsNode = objectMapper.readTree(rawResponse).get("participants").toString();
        var participantList = objectMapper.readValue(participantsNode, new TypeReference<List<Participant>>() {
        });
        assertThat(participantList).containsOnly(participantList.toArray(Participant[]::new));
    }

    @Test
    void shouldReturnParticipantWithNotNullId() throws Exception {
        var p = participantForSaving;
        doReturn(Participant.of(EXPECTED_NEW_ID, p.getFirstName(), p.getLastName(), p.getAge()+100, p.getCity()))
                .when(participantService).save(Mockito.any());
        doReturn("").when(translator).toLocale(anyString());
        var rawResponse = mockMvc.perform(post(URI_ADD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(participantForSaving)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var rootNode = objectMapper.readTree(rawResponse);
        var actualId = rootNode.get("id").asLong();
        var actualParticipant = objectMapper.readValue(rootNode.get("participant").toString(), Participant.class);
        assertAll(
                () -> assertThat(actualId).isEqualTo(EXPECTED_NEW_ID),
                () -> assertThat(actualParticipant.getId()).isEqualTo(EXPECTED_NEW_ID)
        );
    }
}