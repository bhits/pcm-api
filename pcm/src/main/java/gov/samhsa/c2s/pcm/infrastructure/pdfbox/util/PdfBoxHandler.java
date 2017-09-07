package gov.samhsa.c2s.pcm.infrastructure.pdfbox.util;

import gov.samhsa.c2s.pcm.infrastructure.pdfbox.PdfBoxFont;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.PdfBoxPageSize;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PdfBoxHandler {
    public static final String TAB_REGEX = "\\t";
    public static final String SPACE_STRING = " ";

    public static PDFont convertPdfBoxFontToPDFont(PdfBoxFont configuredFont) {
        return buildPDFontMap().get(configuredFont);
    }

    public static PDRectangle convertPdfBoxPageSizeToPDRectangle(PdfBoxPageSize configuredPageSize) {
        return buildPDRectangleMap().get(configuredPageSize);
    }

    public static float targetedStringWidth(String text, PDFont font, float fontSize) throws IOException {
        return font.getStringWidth(text.replaceAll(TAB_REGEX, SPACE_STRING)) * fontSize / 1000F;
    }

    public static float targetedStringHeight(PDFont font, float fontSize) throws IOException {
        return font.getFontDescriptor().getFontBoundingBox().getHeight() * fontSize / 1000F;
    }

    public static String formatLocalDate(LocalDate localDate, String formatPattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
        return localDate.format(formatter);
    }

    private static Map<PdfBoxFont, PDFont> buildPDFontMap() {
        return Collections.unmodifiableMap(Stream.of(
                new AbstractMap.SimpleEntry<>(PdfBoxFont.TIMES_ROMAN, PDType1Font.TIMES_ROMAN),
                new AbstractMap.SimpleEntry<>(PdfBoxFont.TIMES_BOLD, PDType1Font.TIMES_BOLD),
                new AbstractMap.SimpleEntry<>(PdfBoxFont.TIMES_ITALIC, PDType1Font.TIMES_ITALIC),
                new AbstractMap.SimpleEntry<>(PdfBoxFont.TIMES_BOLD_ITALIC, PDType1Font.TIMES_BOLD_ITALIC),
                new AbstractMap.SimpleEntry<>(PdfBoxFont.HELVETICA, PDType1Font.HELVETICA),
                new AbstractMap.SimpleEntry<>(PdfBoxFont.HELVETICA_BOLD, PDType1Font.HELVETICA_BOLD),
                new AbstractMap.SimpleEntry<>(PdfBoxFont.HELVETICA_OBLIQUE, PDType1Font.HELVETICA_OBLIQUE),
                new AbstractMap.SimpleEntry<>(PdfBoxFont.HELVETICA_BOLD_OBLIQUE, PDType1Font.HELVETICA_BOLD_OBLIQUE),
                new AbstractMap.SimpleEntry<>(PdfBoxFont.COURIER, PDType1Font.COURIER),
                new AbstractMap.SimpleEntry<>(PdfBoxFont.COURIER_BOLD, PDType1Font.COURIER_BOLD),
                new AbstractMap.SimpleEntry<>(PdfBoxFont.COURIER_OBLIQUE, PDType1Font.COURIER_OBLIQUE),
                new AbstractMap.SimpleEntry<>(PdfBoxFont.COURIER_BOLD_OBLIQUE, PDType1Font.COURIER_BOLD_OBLIQUE),
                new AbstractMap.SimpleEntry<>(PdfBoxFont.SYMBOL, PDType1Font.SYMBOL),
                new AbstractMap.SimpleEntry<>(PdfBoxFont.ZAPF_DINGBATS, PDType1Font.ZAPF_DINGBATS))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)));
    }

    private static Map<PdfBoxPageSize, PDRectangle> buildPDRectangleMap() {
        return Collections.unmodifiableMap(Stream.of(
                new AbstractMap.SimpleEntry<>(PdfBoxPageSize.A0, PDRectangle.A0),
                new AbstractMap.SimpleEntry<>(PdfBoxPageSize.A1, PDRectangle.A1),
                new AbstractMap.SimpleEntry<>(PdfBoxPageSize.A2, PDRectangle.A2),
                new AbstractMap.SimpleEntry<>(PdfBoxPageSize.A3, PDRectangle.A3),
                new AbstractMap.SimpleEntry<>(PdfBoxPageSize.A4, PDRectangle.A4),
                new AbstractMap.SimpleEntry<>(PdfBoxPageSize.A5, PDRectangle.A5),
                new AbstractMap.SimpleEntry<>(PdfBoxPageSize.A6, PDRectangle.A6),
                new AbstractMap.SimpleEntry<>(PdfBoxPageSize.LEGAL, PDRectangle.LEGAL),
                new AbstractMap.SimpleEntry<>(PdfBoxPageSize.LETTER, PDRectangle.LETTER))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)));
    }
}
