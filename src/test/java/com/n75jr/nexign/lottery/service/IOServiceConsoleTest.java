package com.n75jr.nexign.lottery.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Console test")
class IOServiceConsoleTest {

    private static final String PHRASE = "All animas are equal, but some animals are more equal than others";

    private IOService ioService;
    private InputStream oldInputStream;
    private PrintStream olPrintStream;
    private ByteArrayOutputStream outBuff;
    private ByteArrayInputStream inBuff;

    @BeforeEach
    void setUp() {
        outBuff = new ByteArrayOutputStream(PHRASE.length());
        oldInputStream = System.in;
        olPrintStream = System.out;
        inBuff = new ByteArrayInputStream(PHRASE.getBytes());
        ioService = new IOServiceConsole(inBuff, new PrintStream(outBuff));
    }

    @Test
    void shouldReadPhraseFromInBuffer() {
        var readString = ioService.readString();

        assertThat(readString).isEqualTo(PHRASE);
    }

    @Test
    void shouldWritePhraseToOutBuffer() {
        ioService.print(PHRASE);

        assertThat(outBuff.toByteArray()).isEqualTo(PHRASE.getBytes());
    }

    @Test
    void shouldWritePhraseToOutBufferWithLineSeparator() {
        ioService.println(PHRASE);

        assertThat(outBuff.toByteArray())
                .isEqualTo((PHRASE + System.lineSeparator()).getBytes());
    }

    @AfterEach
    void tearDown() {
        System.setIn(oldInputStream);
        System.setOut(olPrintStream);
    }
}