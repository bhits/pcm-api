package gov.samhsa.mhc.pcm.infrastructure;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import gov.samhsa.mhc.pcm.domain.consent.Consent;

import java.util.ArrayList;
import java.util.Date;

public interface ITextPdfService {
    //Create document title
    Paragraph createParagraphWithContent(String title, Font font);

    // Create borderless table
    PdfPTable createBorderlessTable(int column);

    PdfPCell createBorderlessCell(String content, Font font);

    Chunk createChunkWithFont(String text, Font textFont);

    Paragraph createCellContent(String label, Font labelFont, String value, Font valueFont);

    PdfPTable createSectionTitle(String title);

    Chunk createUnderlineText(String text);

    PdfPCell createEmptyBorderlessCell();

    PdfPCell createCellWithUnderlineContent(String text);

    List createUnorderList(ArrayList<String> items);

    PdfPTable createConsentReferenceNumberTable(Consent consent);

    PdfPTable createSigningDetailsTable(String firstName, String lastName, String email, boolean isSigned, Date attestedOn);

    PdfPTable createPatientNameAndDOBTable(String firstName, String lastName, Date birthDate);

    String formatDate(Date aDate);

    String getFullName(String firstName, String lastName);
}
