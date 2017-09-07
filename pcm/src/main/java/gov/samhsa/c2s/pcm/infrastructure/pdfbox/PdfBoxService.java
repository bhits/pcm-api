package gov.samhsa.c2s.pcm.infrastructure.pdfbox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public interface PdfBoxService {

    PDPage generatePage(String typeOfPdf, PDDocument generatedPdDocument);

    PDFont getConfiguredPdfFont(String typeOfPdf);

    PDRectangle getConfiguredPdfPageSize(String typeOfPdf);

    void addTextAtOffset(String text, PDFont font, float fontSize, Color textColor, float xCoordinate, float yCoordinate,
                         PDPageContentStream contentStream) throws IOException;

    void addCenteredTextAtOffset(String text, PDFont font, float fontSize, Color textColor, float yCoordinate,
                                 PDPage page, PDPageContentStream contentStream) throws IOException;

    void addTableContent(PDPageContentStream contentStream, TableAttribute tableAttribute,
                         List<List<String>> content) throws IOException;
}
