package com.n75jr.nexign.lottery.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class ErrorResponse {

    private String message;
    private String status;
    private ZonedDateTime timestamp;
}
