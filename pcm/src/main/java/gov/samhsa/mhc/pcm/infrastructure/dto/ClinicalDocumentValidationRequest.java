package gov.samhsa.mhc.pcm.infrastructure.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by Jiahao.Li on 5/29/2016.
 */
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
