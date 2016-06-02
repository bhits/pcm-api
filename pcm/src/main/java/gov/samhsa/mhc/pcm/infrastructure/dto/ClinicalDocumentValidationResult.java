package gov.samhsa.mhc.pcm.infrastructure.dto;

/**
 * Created by Jiahao.Li on 5/29/2016.
 */
public class ClinicalDocumentValidationResult {
    private String documentType;
    private boolean isValidDocument;

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public boolean isValidDocument() {
        return isValidDocument;
    }

    public void setValidDocument(boolean validDocument) {
        isValidDocument = validDocument;
    }
}
