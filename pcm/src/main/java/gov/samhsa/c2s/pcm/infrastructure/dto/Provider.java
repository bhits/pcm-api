package gov.samhsa.c2s.pcm.infrastructure.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class Provider {
    @NotNull
    @Size(max = 10)
    private String npi;

    private EntityType entityType;

    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ]+[-]?[a-zA-ZÀ-ÿ']*[a-zA-ZÀ-ÿ]$")
    private String lastName;

    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ]+[-]?[a-zA-ZÀ-ÿ']*[a-zA-ZÀ-ÿ]$")
    private String firstName;


    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ]+[-]?[a-zA-ZÀ-ÿ']*[a-zA-ZÀ-ÿ]$")
    private String middleName;

    @Size(min = 2, max = 255)
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ]+[-]?[a-zA-ZÀ-ÿ']*[a-zA-ZÀ-ÿ]$")
    private String organizationName;

    private String genderCode;

    private String firstLineBusinessMailingAddress;

    private String secondLineBusinessMailingAddress;

    private String businessMailingAddressCityName;

    private String businessMailingAddressStateName;

    private String businessMailingAddressPostalCode;

    private String businessMailingAddressCountryCode;

    private String businessMailingAddressTelephoneNumber;

    private String businessMailingAddressFaxNumber;

    private String firstLineBusinessPracticeLocationAddress;

    private String secondLineBusinessPracticeLocationAddress;

    private String businessPracticeLocationAddressCityName;

    private String businessPracticeLocationAddressStateName;

    private String businessPracticeLocationAddressPostalCode;

    private String businessPracticeLocationAddressCountryCode;

    private String businessPracticeLocationAddressTelephoneNumber;

    private String businessPracticeLocationAddressFaxNumber;

    private String enumerationDate;

    private String lastUpdateDate;


}