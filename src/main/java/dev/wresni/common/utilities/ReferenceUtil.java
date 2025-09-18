package dev.wresni.common.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReferenceUtil {
    public static String generateReferenceNo(Object suffix, int length) {
        return Objects.isNull(suffix) ?
                String.format("%s-%s", "REF", generateRandomNumber(length)) :
                String.format("%s-%s", suffix.toString(), generateRandomNumber(length));
    }

    public static String generateRandomNumber(int length) {
        Random random = new Random();
        int baseNumber = (int) Math.pow(10, length);
        return String.valueOf(baseNumber + random.nextInt(9 * baseNumber)) ;
    }
}
