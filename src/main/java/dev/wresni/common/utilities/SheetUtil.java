package dev.wresni.common.utilities;

import dev.wresni.common.enums.BooleanStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.CellType;
import org.dhatim.fastexcel.reader.Row;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SheetUtil {
    private static final String EMPTY = "-";

    public static boolean emptyRow(Row row) {
        return row.stream().allMatch(SheetUtil::emptyCell);
    }

    public static boolean nonEmptyRow(Row row) {
        return !emptyRow(row);
    }

    public static boolean emptyCell(Cell cell) {
        return Objects.isNull(cell) ||
                cell.getType().equals(CellType.EMPTY) ||
                cell.getRawValue().equalsIgnoreCase(EMPTY) ||
                StringUtil.isBlank(cell.getRawValue());
    }

    public static String getRawValue(Row row, int column, String defaultValue) {
        if (!row.hasCell(column) ||
                emptyCell(row.getCell(column))) return defaultValue;

        return row.getCellRawValue(column)
                .orElse(defaultValue);
    }

    public static String getRawValue(Row row, int column) {
        return getRawValue(row, column, null);
    }

    public static String getStringValue(Row row, int column, String defaultValue) {
        if (!row.hasCell(column) ||
                emptyCell(row.getCell(column)) ||
                isInvalidCellType(row, column, CellType.STRING)) return defaultValue;

        return row.getCellAsString(column)
                .orElse(defaultValue);
    }

    public static String getStringValue(Row row, int column) {
        return getStringValue(row, column, null);
    }

    public static List<String> getStringListValue(Row row, int column, String separator) {
        String value = getStringValue(row, column);

        return StringUtil.isBlank(value) ?
                Collections.emptyList() :
                Stream.of(value.split(separator))
                        .filter(StringUtil::nonBlank)
                        .map(String::trim)
                        .collect(Collectors.toList());
    }

    public static String getCellStringValue(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell == null || cell.getType() == CellType.EMPTY) return null;

        switch (cell.getType()) {
            case STRING:
                return cell.asString();

            case NUMBER:
                Optional<LocalDate> maybeDate = Optional.ofNullable(cell.asDate().toLocalDate());
                return maybeDate
                        .map(date -> date.format(DateTimeFormatter.ISO_LOCAL_DATE)) // or use custom formatter
                        .orElseGet(cell::getRawValue);

            case BOOLEAN:
                return String.valueOf(cell.asBoolean());

            default:
                return cell.getRawValue();
        }
    }


    public static Integer getIntValue(Row row, int column, Integer defaultValue) {
        if (!row.hasCell(column) ||
                emptyCell(row.getCell(column)) ||
                isInvalidCellType(row, column, CellType.NUMBER)) return defaultValue;

        return row.getCellAsNumber(column)
                .map(BigDecimal::intValue)
                .orElse(defaultValue);
    }

    public static Integer getIntValue(Row row, int column) {
        return getIntValue(row, column, null);
    }

    public static BigDecimal getDecimalValue(Row row, int column, BigDecimal defaultValue) {
        if (!row.hasCell(column) ||
                emptyCell(row.getCell(column)) ||
                isInvalidCellType(row, column, CellType.NUMBER)) {
            return defaultValue;
        }

        return row.getCellAsNumber(column).orElse(defaultValue);
    }

    public static BigDecimal getDecimalValue(Row row, int column) {
        return getDecimalValue(row, column, null);
    }

    public static Long getLongValue(Row row, int column, Long defaultValue) {
        if (!row.hasCell(column) ||
                emptyCell(row.getCell(column)) ||
                isInvalidCellType(row, column, CellType.NUMBER)) return defaultValue;

        return row.getCellAsNumber(column)
                .map(BigDecimal::longValue)
                .orElse(defaultValue);
    }

    public static Long getLongValue(Row row, int column) {
        return getLongValue(row, column, null);
    }

    public static Double getDoubleValue(Row row, int column, Double defaultValue) {
        if (!row.hasCell(column) ||
                emptyCell(row.getCell(column)) ||
                isInvalidCellType(row, column, CellType.NUMBER)) return defaultValue;

        return row.getCellAsNumber(column)
                .map(BigDecimal::doubleValue)
                .orElse(defaultValue);
    }

    public static Double getDoubleValue(Row row, int column) {
        return getDoubleValue(row, column, null);
    }

    public static BigDecimal getBigDecimalValue(Row row, int column, BigDecimal defaultValue) {
        if (!row.hasCell(column) ||
                emptyCell(row.getCell(column)) ||
                isInvalidCellType(row, column, CellType.NUMBER)) return defaultValue;

        return row.getCellAsNumber(column)
                .orElse(defaultValue);
    }

    public static BigDecimal getBigDecimalValue(Row row, int column) {
        return getBigDecimalValue(row, column, null);
    }

    public static BigDecimal getBigDecimalValueSpecial(Row row, int column, BigDecimal defaultValue) {
        Cell cell = row.getCell(column);

        if (Objects.isNull(cell)) {
            return defaultValue;
        }

        return Optional.ofNullable(getCellAsBigDecimal(cell, defaultValue))
                .orElse(defaultValue);
    }

    private static BigDecimal getCellAsBigDecimal(Cell cell, BigDecimal defaultValue) {
        return switch (cell.getType()) {
            case NUMBER -> cell.asNumber();
            case STRING -> parseBigDecimal(cell.asString(), defaultValue);
            default -> defaultValue;
        };
    }

    private static BigDecimal parseBigDecimal(String value, BigDecimal defaultValue) {
        try {
            return new BigDecimal(value.replace(",", ".").trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static BooleanStatus getBooleanValue(Row row, int column, BooleanStatus defaultValue) {
        if (!row.hasCell(column) ||
                emptyCell(row.getCell(column)) ||
                isInvalidCellType(row, column, CellType.STRING)) {
            return defaultValue;
        }

        return row.getCellAsString(column)
                .map(BooleanStatus::of)
                .orElse(defaultValue);
    }

    public static BooleanStatus getBooleanValue(Row row, int column) {
        return getBooleanValue(row, column, null);
    }


    public static LocalDate getDateValue(Row row, int column) {
        if (!row.hasCell(column) ||
                emptyCell(row.getCell(column)) ||
                isInvalidCellType(row, column, CellType.NUMBER)) return null;

        return row.getCellAsDate(column)
                .map(LocalDateTime::toLocalDate)
                .orElse(null);
    }

    public static LocalTime getTimeValue(Row row, int column) {
        if (!row.hasCell(column) ||
                emptyCell(row.getCell(column)) ||
                isInvalidCellType(row, column, CellType.NUMBER)) return null;

        return row.getCellAsDate(column)
                .map(LocalDateTime::toLocalTime)
                .orElse(null);
    }

    public static boolean isValidCellType(Row row, int column, CellType type) {
        return row.getCell(column).getType().equals(type);
    }

    public static boolean isInvalidCellType(Row row, int column, CellType type) {
        return !isValidCellType(row, column, type);
    }
}
