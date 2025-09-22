package dev.wresni.common.blueprints.jackson.serializers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import dev.wresni.common.utilities.DateUtil;
import dev.wresni.common.utilities.ObjectUtil;
import dev.wresni.common.utilities.StringUtil;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class LocalDateDefaultDeserializer extends JsonDeserializer<LocalDate> implements ContextualDeserializer {
    private Collection<String> formats;

    public LocalDateDefaultDeserializer() {
        formats = DateUtil.dateFormats();
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING)
            return deserializeFromString(jsonParser);
        else if (jsonParser.getCurrentToken() == JsonToken.START_ARRAY)
            return deserializeFromArray(jsonParser);
        else return deserializeFromTimestamp(jsonParser);
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext context, BeanProperty property) {
        return Optional.ofNullable(property)
                .map(p -> p.getAnnotation(JsonFormat.class) != null ? context.getAnnotationIntrospector().findFormat(p.getMember()) : null)
                .map(JsonFormat.Value::getPattern)
                .filter(StringUtil::nonBlank)
                .map(this::addFormat)
                .orElse(this);
    }

    private JsonDeserializer<LocalDate> addFormat(String format) {
        formats.add(format);
        return this;
    }

    private LocalDate deserializeFromString(JsonParser jsonParser) throws IOException {
        String rawValue = jsonParser.getValueAsString();
        return StringUtil.isBlank(rawValue) ?
                null :
                formats.stream()
                        .map(format -> parsingDate(rawValue, format))
                        .filter(Objects::nonNull)
                        .findFirst()
                        .orElse(null);
    }

    private LocalDate parsingDate(String rawValue, String format) {
        try {
            return LocalDate.parse(rawValue, DateTimeFormatter.ofPattern(format));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private LocalDate deserializeFromArray(JsonParser parser) throws IOException {
        int[] datetimeArray = new int[7];
        int id = 0;

        parser.nextToken();
        while (parser.getCurrentToken() != JsonToken.END_ARRAY) {
            datetimeArray[id++] = parser.getIntValue();
            parser.nextToken();
        }

        while (id < 7) {
            datetimeArray[id++] = 0;
        }

        return LocalDateTime.of(datetimeArray[0], datetimeArray[1], datetimeArray[2], datetimeArray[3], datetimeArray[4], datetimeArray[5], datetimeArray[6]).toLocalDate();
    }

    private LocalDate deserializeFromTimestamp(JsonParser jsonParser) throws IOException {
        return ObjectUtil.mapOr(DateUtil.toLocalDateTime(jsonParser.getLongValue()), LocalDateTime::toLocalDate, null);
    }
}
