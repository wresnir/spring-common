package dev.wresni.common.utilities;

import dev.wresni.common.enums.MimeType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtil {
    public static Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public static MimeType getMimeType(String filename) {
        return MimeType.fromExtension(getExtension(filename).orElse(null));
    }

}
