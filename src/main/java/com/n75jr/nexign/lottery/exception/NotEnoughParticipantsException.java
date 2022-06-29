package com.n75jr.nexign.lottery.exception;

public class NotEnoughParticipantsException extends RuntimeException {

    public NotEnoughParticipantsException() {
    }

    public NotEnoughParticipantsException(String message) {
        super(message);
    }
}
