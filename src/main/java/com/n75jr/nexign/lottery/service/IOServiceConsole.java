package com.n75jr.nexign.lottery.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Log4j2
@Service
public class IOServiceConsole implements IOService {

    private final Scanner in;
    private final PrintStream out;

    public IOServiceConsole(@Value("#{ T(System).in }") InputStream in,
                            @Value("#{ T(System).out }") PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    @Override
    public String readString() {
        if (log.isTraceEnabled()) {
            log.trace("Read string");
        }
        return in.nextLine();
    }

    @Override
    public void print(Object message) {
        if (log.isTraceEnabled()) {
            log.trace("Print message: \"{}\"", message);
        }
        out.print(message);
    }

    @Override
    public void println(Object message) {
        if (log.isTraceEnabled()) {
            log.trace("Println message: \"{}\"", message);
        }
        out.println(message);
    }
}
