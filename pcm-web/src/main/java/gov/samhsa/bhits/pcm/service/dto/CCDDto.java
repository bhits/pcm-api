package gov.samhsa.bhits.pcm.service.dto;

import java.nio.charset.StandardCharsets;

/**
 * Created by tomson.ngassa on 1/22/2016.
 */
public class CCDDto {

    private byte[] ccdFile;

    public CCDDto() {
    }

    public CCDDto(byte[] ccdFile) {
        this.ccdFile = ccdFile;
    }

    public byte[] getCCDFile() {
        return ccdFile;
    }

    public void setCCDFile(byte[] ccdFile) {
        this.ccdFile = ccdFile;
    }

    @Override
    public String toString() {
        return this.ccdFile == null ? "" : new String(this.getCCDFile(), StandardCharsets.UTF_8);
    }
}
