package gov.samhsa.c2s.vss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "c2s.vss")
@Data
public class VssProperties {

    @NotNull
    @Min(0)
    private int conceptCodeListPageSize;
}