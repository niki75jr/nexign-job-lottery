package com.n75jr.nexign.lottery.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@JsonIgnoreProperties(value = "hibernateLazyInitializer", ignoreUnknown = true)
public class RandomParticipantDTO {

    @JsonIgnore
    private Long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonIgnore
    private Integer age;
    private String city;

    @JsonProperty("address")
    private void unpackNestedCity(Map<String, Object> address) {
        if (log.isTraceEnabled()) {
            log.trace("Unpacking a nested city");
        }
        this.city = (String) address.get("city");
    }
}
