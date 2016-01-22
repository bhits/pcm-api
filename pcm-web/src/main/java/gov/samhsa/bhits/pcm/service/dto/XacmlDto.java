package gov.samhsa.bhits.pcm.service.dto;

import java.nio.charset.StandardCharsets;

public class XacmlDto {
    private byte[] xacmlFile;

    public XacmlDto() {
    }

    public XacmlDto(byte[] xacmlFile) {
        this.xacmlFile = xacmlFile;
    }

    public byte[] getXacmlFile() {
        return xacmlFile;
    }

    public void setXacmlFile(byte[] xacmlFile) {
        this.xacmlFile = xacmlFile;
    }

    @Override
    public String toString() {
        return this.xacmlFile == null ? "" : new String(this.getXacmlFile(), StandardCharsets.UTF_8);
    }
}
