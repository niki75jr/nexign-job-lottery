package com.n75jr.nexign.lottery.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class ValidationErrorResponse {

    private final List<Violation> violations;
}
