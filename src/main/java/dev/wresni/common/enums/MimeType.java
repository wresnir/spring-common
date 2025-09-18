package dev.wresni.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.MediaType;

@Getter
@AllArgsConstructor
public enum MimeType {
    IMAGE_JPG ("jpg", MediaType.IMAGE_JPEG_VALUE),
    IMAGE_JPEG ("jpeg", MediaType.IMAGE_JPEG_VALUE),
    IMAGE_JFIF ("jfif", MediaType.IMAGE_JPEG_VALUE),
    IMAGE_PNG ("png", MediaType.IMAGE_PNG_VALUE),
    IMAGE_GIF ("gif", MediaType.IMAGE_GIF_VALUE),
    PDF ("pdf", MediaType.APPLICATION_PDF_VALUE),
    OFFICE_WORD ("doc", "application/msword"),
    OFFICE_WORD_T ("dot", "application/msword"),
    OFFICE_WORD_X ("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    OFFICE_WORD_TX ("dotx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template"),
    OFFICE_WORD_M ("docm", "application/vnd.ms-word.document.macroEnabled.12"),
    OFFICE_WORD_TM ("dotm", "application/vnd.ms-word.template.macroEnabled.12"),
    OFFICE_EXCEL ("xls", "application/vnd.ms-excel"),
    OFFICE_EXCEL_T ("xlt", "application/vnd.ms-excel"),
    OFFICE_EXCEL_A ("xla", "application/vnd.ms-excel"),
    OFFICE_EXCEL_X ("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    OFFICE_EXCEL_XT ("xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template"),
    OFFICE_EXCEL_M ("xlsm", "application/vnd.ms-excel.sheet.macroEnabled.12"),
    OFFICE_EXCEL_TM ("xltm", "application/vnd.ms-excel.template.macroEnabled.12"),
    OFFICE_EXCEL_AM ("xlam", "application/vnd.ms-excel.addin.macroEnabled.12"),
    OFFICE_EXCEL_B ("xlsb", "application/vnd.ms-excel.sheet.binary.macroEnabled.12"),
    OFFICE_POWERPOINT ("ppt", "application/vnd.ms-powerpoint"),
    OFFICE_POWERPOINT_T ("pot", "application/vnd.ms-powerpoint"),
    OFFICE_POWERPOINT_S ("pps", "application/vnd.ms-powerpoint"),
    OFFICE_POWERPOINT_A ("ppa", "application/vnd.ms-powerpoint"),
    OFFICE_POWERPOINT_X ("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    OFFICE_POWERPOINT_XT ("potx", "application/vnd.openxmlformats-officedocument.presentationml.template"),
    OFFICE_POWERPOINT_XS ("ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow"),
    OFFICE_POWERPOINT_AM ("ppam", "application/vnd.ms-powerpoint.addin.macroEnabled.12"),
    OFFICE_POWERPOINT_M ("pptm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12"),
    OFFICE_POWERPOINT_TM ("potm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12"),
    OFFICE_POWERPOINT_SM ("ppsm", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12"),
    CSV ("csv","text/csv"),
    UNKNOWN ("*", MediaType.TEXT_PLAIN_VALUE);

    private final String extension;
    private final String contentType;

    public static MimeType fromExtension(String extension) {
        for (MimeType type : values()) {
            if (type.extension.equalsIgnoreCase(extension.trim()))
                return type;
        }

        return UNKNOWN;
    }

    public MediaType toMediaType() {
        String[] values = contentType.split("/");
        return new MediaType(values[0], values[1]);
    }
}

