package com.n75jr.nexign.lottery.util;

import com.n75jr.nexign.lottery.domain.Participant;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

@UtilityClass
public class Utils {

    public static final int MIN_AGE = 18;
    public static final String[] FIRST_NAMES = {
            "Ivan", "Pert", "Alexander", "Sidor", "Nikita", "Maksim", "Albert"
    };
    public static final String[] LAST_NAMES = {
            "Ivanov", "Pertov", "Kolosov", "Sidorov", "Nikitenko", "Maksimenko"
    };
    public static final String[] CITIES = {
            "Vladivostok", "Khabarovsk", "Chita", "Moscow", "SpB", "Irkutsk"
    };

    public List<Participant> generateParticipants(int size) {
        return generateParticipants(size, false);
    }

    public List<Participant> generateParticipants(int size, boolean nullId) {
        var participants = new ArrayList<Participant>();
        for (int i = 0; i < size; i++) {
            Long id = null;
            if (!nullId) {
                id = (long) i;
            }
            participants.add(
                    Participant.of(
                            id,
                            FIRST_NAMES[current().nextInt(FIRST_NAMES.length)],
                            LAST_NAMES[current().nextInt(LAST_NAMES.length)],
                            current().nextInt(100) + MIN_AGE,
                            CITIES[current().nextInt(CITIES.length)]
                    )
            );
        }
        return participants;
    }
}
