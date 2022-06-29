package com.n75jr.nexign.lottery.i18n;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class Translator {

    private final MessageSource messageSource;

    public String toLocale(String messageCode, String... args) {
        var locale = LocaleContextHolder.getLocale();
        if (log.isTraceEnabled()) {
            log.trace("Translating {} to {}", messageCode, locale);
        }
        return messageSource.getMessage(messageCode, args, locale);
    }
}
