package gov.samhsa.c2s.pcm.infrastructure.pdfbox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.IOException;
import java.util.List;

public interface PdfBoxService {

    PDPage generatePage(String typeOfPdf, PDDocument generatedPdDocument);

    PDFont getConfiguredPdfFont(String typeOfPdf);

    PDRectangle getConfiguredPdfPageSize(String typeOfPdf);

    void addTableContent(PDPageContentStream contentStream, TableAttribute tableAttribute,
                         List<List<String>> content) throws IOException;
}
