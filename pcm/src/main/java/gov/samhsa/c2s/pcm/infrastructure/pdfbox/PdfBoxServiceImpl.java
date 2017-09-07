package gov.samhsa.c2s.pcm.infrastructure.pdfbox;

import gov.samhsa.c2s.pcm.config.PdfProperties;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.util.PdfBoxHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PdfBoxServiceImpl implements PdfBoxService {

    @Autowired
    private PdfProperties pdfProperties;

    @Override
    public PDPage generatePage(String typeOfPdf, PDDocument generatedPdDocument) {
        PDPage page = new PDPage();
        page.setMediaBox(getConfiguredPdfPageSize(typeOfPdf));
        generatedPdDocument.addPage(page);
        return page;
    }

    /**
     * Get the configured font and will set TIMES ROMAN as default font if it is not configured.
     *
     * @param typeOfPdf
     * @return
     */
    @Override
    public PDFont getConfiguredPdfFont(String typeOfPdf) {
        return pdfProperties.getPdfConfigs().stream()
                .filter(pdfConfig -> pdfConfig.type.equalsIgnoreCase(typeOfPdf))
                .map(pdfConfig -> PdfBoxHandler.convertPdfBoxFontToPDFont(pdfConfig.pdFont))
                .findAny()
                .orElse(PDType1Font.TIMES_ROMAN);
    }

    /**
     * Get the configured page size and will set LETTER as default size if it is not configured.
     *
     * @param typeOfPdf
     * @return
     */
    @Override
    public PDRectangle getConfiguredPdfPageSize(String typeOfPdf) {
        return pdfProperties.getPdfConfigs().stream()
                .filter(pdfConfig -> pdfConfig.type.equalsIgnoreCase(typeOfPdf))
                .map(pdfConfig -> PdfBoxHandler.convertPdfBoxPageSizeToPDRectangle(pdfConfig.pdfPageSize))
                .findAny()
                .orElse(PDRectangle.LETTER);
    }
}
