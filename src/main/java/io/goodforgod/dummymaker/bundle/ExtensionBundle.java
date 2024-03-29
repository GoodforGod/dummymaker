package io.goodforgod.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * File extensions bundle
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.7.2020
 */
public final class ExtensionBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "aif", "json", "cda", "mid", "midi", "mp3", "mpa", "ogg", "wav", "wma", "wpl", "7z", "arj", "deb", "pkg", "rar",
            "rpm", "tar.gz", "z", "zip", "bin", "dmg", "iso", "toast", "vcd", "csv", "dat", "db", "dbf", "log", "mdb", "sav",
            "sql", "tar", "xml", "email", "eml", "emlx", "msg", "oft", "ost", "pst", "vcf", "apk", "bat", "bin", "cgi", "pl",
            "com", "exe", "gadget", "jar", "msi", "py", "wsf", "fnt", "fon", "otf", "ttf", "ai", "bmp", "gif", "ico", "jpeg",
            "jpg", "png", "ps", "psd", "svg", "tif", "tiff", "asp", "aspx", "cer", "cfm", "cgi", "pl", "css", "htm", "html", "js",
            "jsp", "part", "php", "py", "rss", "xhtml", "key", "odp", "pps", "ppt", "pptx", "c", "class", "cpp", "cs", "h",
            "java", "pl", "sh", "swift", "vb", "ods", "xls", "xlsm", "xlsx", "bak", "cab", "cfg", "cpl", "cur", "dll", "dmp",
            "drv", "icns", "ico", "ini", "lnk", "msi", "sys", "tmp", "3g2", "3gp", "avi", "flv", "h264", "m4v", "mkv", "mov",
            "mp4", "mpg", "mpeg", "rm", "swf", "vob", "wmv", "doc", "docx", "odt", "pdf", "rtf", "tex", "txt", "wpd");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_ENGLISH;
    }
}
