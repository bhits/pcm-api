package gov.samhsa.c2s.pcm.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "c2s.pcm")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PcmProperties {

    @NotNull
    @Valid
    private ClinicalData clinicaldata;

    @NotNull
    @Valid
    private Pid pid;

    @NotNull
    @Valid
    private Pagination pagination;

    @NotNull
    @Valid
    private Activity activity;

    @NotNull
    @Valid
    private HieConnection hieConnection;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pid {
        @Valid
        private Domain domain;

        @NotEmpty
        private String org;

        @NotEmpty
        private String prefix;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Domain {
            @NotEmpty
            private String id;

            @NotEmpty
            private String system;

            @NotEmpty
            private String type;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pagination {
        @NotNull
        @Min(0)
        private int itemsPerPage;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Activity {
        @Valid
        private Sql sql;

        @Data
        public static class Sql {
            @NotEmpty
            private String path;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HieConnection {
        @Valid
        private Fhir fhir;

        @Data
        public static class Fhir {
            @NotNull
            private boolean enabled;

            @NotEmpty
            private String serverUrl;

            @NotEmpty
            private String clientSocketTimeoutInMs;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClinicalData{
        @Min(0)
        private long maximumUploadFileSize;

        @NotEmpty
        private String extensionsPermittedToUpload;
    }
}