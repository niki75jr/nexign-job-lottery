package com.n75jr.nexign.lottery.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

@Log4j2
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final List<Locale> LOCALES = List.of(
            new Locale("en"),
            new Locale("ru")
    );

    @Bean
    public LocaleResolver localeResolver() {
        if (log.isTraceEnabled()) {
            log.trace("@Bean restTemplate localeResolver with {} locales", LOCALES.size());
        }
        var headerLocaleResolver = new AcceptHeaderLocaleResolver();
        headerLocaleResolver.setSupportedLocales(LOCALES);
        headerLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return headerLocaleResolver;
    }
}
