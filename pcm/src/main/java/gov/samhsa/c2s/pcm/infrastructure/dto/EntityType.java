package gov.samhsa.c2s.pcm.infrastructure.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EntityType {
    /*
    entityTypeCode =1 Individual
    entityTypeCode =2 Organization
    */
    @NotNull
    private String code;
    private String displayName;
}

