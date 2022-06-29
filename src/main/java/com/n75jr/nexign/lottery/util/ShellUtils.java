package com.n75jr.nexign.lottery.util;

import com.n75jr.nexign.lottery.service.IOService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Function;

@Component
@NoArgsConstructor
public final class ShellUtils {

    public static int getLengthOfNum(long n) {
        return String.valueOf(n).length();
    }

    public static <T> void printItems(Collection<T> items, IOService iOservice, Function<T, String> toStr) {
        var counter = 1L;
        var maxLengthOfNum = ShellUtils.getLengthOfNum(items.size());
        var newLine = System.lineSeparator();
        StringBuilder sb = new StringBuilder();

        for (var i : items) {
            sb.append(String.format(String.join("", "%", String.valueOf(maxLengthOfNum), "d"), counter++))
                    .append(") ")
                    .append(toStr.apply(i))
                    .append(newLine);
        }
        iOservice.println(sb);
    }
}
