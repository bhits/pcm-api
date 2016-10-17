package gov.samhsa.c2s.pcm.infrastructure.dto;

import javax.validation.constraints.NotNull;

public class ClinicalDocumentValidationRequest {
    @NotNull
    private byte[] document;

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }
}
