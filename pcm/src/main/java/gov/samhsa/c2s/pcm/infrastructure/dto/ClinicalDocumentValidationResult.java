package gov.samhsa.c2s.pcm.infrastructure.dto;

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
