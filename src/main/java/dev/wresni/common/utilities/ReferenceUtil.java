package dev.wresni.common.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReferenceUtil {
    public static String generateReferenceNo(Object suffix, int length) {
        return Objects.isNull(suffix) ?
                String.format("%s-%s", "REF", generateRandomNumber(length)) :
                String.format("%s-%s", suffix, generateRandomNumber(length));
    }

    public static String generateTimedReferenceNo(Object suffix, int length) {
        String datetime = DateUtil.toFormat(LocalDateTime.now(), DateUtil.DATETIME_INDEX_FORMAT);
        return Objects.isNull(suffix) ?
                String.format("%s-%s-%s", "REF", datetime, generateRandomNumber(length)) :
                String.format("%s-%s-%s", suffix, datetime, generateRandomNumber(length));
    }

    public static String generateRandomNumber(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) sb.append((int) (Math.random() * 10));

        return sb.toString();
    }
}
