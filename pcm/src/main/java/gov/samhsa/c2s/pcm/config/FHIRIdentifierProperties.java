package gov.samhsa.c2s.pcm.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "c2s.pcm.config")
@Data
public class FHIRIdentifierProperties {

    private Pid pid;
    private Pou pou;
    private ConsentType consentType;
    private Npi npi;
    private String keepExcludeList;

    @Data
    public static class Coding{
        private String system;
        private String oid;
        private String label;
    }

    @Data
    public static class ConsentType extends Coding{
        private String code;
    }

    @Data
    public static class Npi extends Coding{ }

    @Data
    public static class Pou extends Coding{ }

    @Data
    public static class Domain extends Coding{ }
    @Data
    public static class Pid {
        private Domain domain;
    }

}