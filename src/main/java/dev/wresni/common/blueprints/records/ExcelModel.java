package dev.wresni.common.blueprints.records;

import dev.wresni.common.utilities.CollectionUtil;
import dev.wresni.common.utilities.StringUtil;
import lombok.Builder;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Builder
public record ExcelModel(
        OutputStream outputStream,
        Workbook book,
        String filename,
        String filepath,
        Map<String, Worksheet> sheets
) {
    public static ExcelModel of(String filename, String filepath, String workbookName) throws IOException {
        File directory = new File(StringUtil.isBlank(filepath) ? "" : filepath);
        Path dirpath = Paths.get(directory.getAbsolutePath(), filename + ".xlsx");
        OutputStream os = Files.newOutputStream(dirpath);
        Workbook book = new Workbook(os, workbookName, "1.0");

        return ExcelModel.builder()
                .outputStream(os)
                .book(book)
                .filename(filename)
                .filepath(filepath)
                .sheets(new HashMap<>())
                .build();
    }

    public Worksheet addSheet(String worksheetName) {
        Worksheet sheet = book.newWorksheet(worksheetName);
        sheets.put(worksheetName, sheet);
        return sheet;
    }

    public void close() throws IOException {
        if (CollectionUtil.nonBlank(sheets))
            for (Worksheet sheet : sheets.values())
                sheet.close();

        book.finish();
        outputStream.close();
    }
}
