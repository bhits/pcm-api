package gov.samhsa.c2s.pcm.infrastructure.pdfbox;

import gov.samhsa.c2s.pcm.config.PdfProperties;
import gov.samhsa.c2s.pcm.infrastructure.exception.InvalidTableAttributeException;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.util.PdfBoxHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void addTableContent(PDPageContentStream contentStream, TableAttribute tableAttribute, List<List<String>> content) throws IOException {
        assertValidTableAttribute(tableAttribute);
        String[][] tableContent = content.stream()
                .map(u -> u.toArray(new String[0])).toArray(String[][]::new);

        // Draw table line
        drawTableLine(contentStream, tableAttribute, tableContent);

        // Fill the content to table
        fillTextToTable(contentStream, tableAttribute, tableContent);
    }

    private void drawTableLine(PDPageContentStream contentStream, TableAttribute tableAttribute, String[][] tableContent) throws IOException {
        final int rows = tableContent.length;
        final int cols = tableContent[0].length;
        final float rowHeight = Optional.of(tableAttribute.getRowHeight())
                .orElse(PdfBoxStyle.DEFAULT_TABLE_ROW_HEIGHT);
        log.debug("The row height of the table is: " + rowHeight);

        //set border color
        contentStream.setStrokingColor(tableAttribute.getBorderColor());

        //draw the rows
        final float tableWidth = calculateTableWidth(tableAttribute.getColumns());
        log.debug("The number of the table rows is: " + tableAttribute.getColumns().size());
        float nextLineY = tableAttribute.getYCoordinate();
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(tableAttribute.getXCoordinate(), nextLineY);
            contentStream.lineTo(tableAttribute.getXCoordinate() + tableWidth, nextLineY);
            contentStream.stroke();
            nextLineY -= rowHeight;
        }

        //draw the columns
        final float tableHeight = rowHeight * rows;
        float nextLineX = tableAttribute.getXCoordinate();
        for (int i = 0; i < cols; i++) {
            contentStream.moveTo(nextLineX, tableAttribute.getYCoordinate());
            contentStream.lineTo(nextLineX, tableAttribute.getYCoordinate() - tableHeight);
            contentStream.stroke();
            nextLineX += tableAttribute.getColumns().get(i).getCellWidth();
        }
        //draw the right border
        contentStream.moveTo(nextLineX, tableAttribute.getYCoordinate());
        contentStream.lineTo(nextLineX, tableAttribute.getYCoordinate() - tableHeight);
        contentStream.stroke();
    }

    private void fillTextToTable(PDPageContentStream contentStream, TableAttribute tableAttribute, String[][] tableContent) throws IOException {
        //Set text font and font size
        contentStream.setFont(tableAttribute.getContentFont(), tableAttribute.getContentFontSize());

        final float cellMargin = tableAttribute.getCellMargin();
        // Define to start drawing content at horizontal position
        float nextTextX = tableAttribute.getXCoordinate() + cellMargin;
        // Define to start drawing content at vertical position
        float nextTextY = calculateDrawPositionInVertical(tableAttribute);

        for (String[] aContent : tableContent) {
            int index = 0;
            for (String text : aContent) {
                contentStream.beginText();
                contentStream.newLineAtOffset(nextTextX, nextTextY);
                contentStream.showText(text != null ? text : "");
                contentStream.endText();
                nextTextX += tableAttribute.getColumns().get(index).getCellWidth();
                index++;
            }
            // Update new position cursor after writing the content for one row
            nextTextY -= tableAttribute.getRowHeight();
            nextTextX = tableAttribute.getXCoordinate() + cellMargin;
        }
    }

    private float calculateTableWidth(List<Column> columns) {
        final float initTableWidth = 0f;
        Double tableWidth = columns.stream()
                .mapToDouble(column -> column.getCellWidth() + initTableWidth)
                .sum();
        return tableWidth.floatValue();
    }

    private float calculateDrawPositionInVertical(TableAttribute tableAttribute) {
        return tableAttribute.getYCoordinate() - (tableAttribute.getRowHeight() / 2)
                - ((tableAttribute.getContentFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * tableAttribute.getContentFontSize()) / 4);
    }

    private void assertValidTableAttribute(TableAttribute tableAttribute) {
        if (tableAttribute.getColumns() == null || tableAttribute.getColumns().isEmpty()) {
            throw new InvalidTableAttributeException("The columns in the table must be configured.");
        }
    }
}
