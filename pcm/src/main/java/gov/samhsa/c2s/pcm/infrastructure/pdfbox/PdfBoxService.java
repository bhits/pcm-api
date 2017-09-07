package gov.samhsa.c2s.pcm.infrastructure.pdfbox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

public interface PdfBoxService {

    PDPage generatePage(String typeOfPdf, PDDocument generatedPdDocument);

    PDFont getConfiguredPdfFont(String typeOfPdf);

    PDRectangle getConfiguredPdfPageSize(String typeOfPdf);
}
