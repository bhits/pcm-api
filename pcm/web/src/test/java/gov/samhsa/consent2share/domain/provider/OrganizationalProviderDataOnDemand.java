package gov.samhsa.consent2share.domain.provider;

import gov.samhsa.consent2share.domain.reference.EntityType;
import gov.samhsa.consent2share.service.provider.OrganizationalProviderService;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Configurable
@Component
public class OrganizationalProviderDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<OrganizationalProvider> data;

	@Autowired
    OrganizationalProviderService organizationalProviderService;

	@Autowired
    OrganizationalProviderRepository organizationalProviderRepository;

	public OrganizationalProvider getNewTransientOrganizationalProvider(int index) {
        OrganizationalProvider obj = new OrganizationalProvider();
        setAuthorizedOfficialFirstName(obj, index);
        setAuthorizedOfficialLastName(obj, index);
        setAuthorizedOfficialNamePrefix(obj, index);
        setAuthorizedOfficialTelephoneNumber(obj, index);
        setAuthorizedOfficialTitle(obj, index);
        setEntityType(obj, index);
        setEnumerationDate(obj, index);
        setFirstLineMailingAddress(obj, index);
        setFirstLinePracticeLocationAddress(obj, index);
        setLastUpdateDate(obj, index);
        setMailingAddressCityName(obj, index);
        setMailingAddressCountryCode(obj, index);
        setMailingAddressFaxNumber(obj, index);
        setMailingAddressPostalCode(obj, index);
        setMailingAddressStateName(obj, index);
        setMailingAddressTelephoneNumber(obj, index);
        setNpi(obj, index);
        setOrgName(obj, index);
        setOtherOrgName(obj, index);
        setPracticeLocationAddressCityName(obj, index);
        setPracticeLocationAddressCountryCode(obj, index);
        setPracticeLocationAddressFaxNumber(obj, index);
        setPracticeLocationAddressPostalCode(obj, index);
        setPracticeLocationAddressStateName(obj, index);
        setPracticeLocationAddressTelephoneNumber(obj, index);
        setProviderTaxonomyCode(obj, index);
        setProviderTaxonomyDescription(obj, index);
        setSecondLineMailingAddress(obj, index);
        setSecondLinePracticeLocationAddress(obj, index);
        return obj;
    }

	public void setAuthorizedOfficialFirstName(OrganizationalProvider obj, int index) {
        String authorizedOfficialFirstName = "authorizedOfficialFirstName_" + index;
        if (authorizedOfficialFirstName.length() > 30) {
            authorizedOfficialFirstName = authorizedOfficialFirstName.substring(0, 30);
        }
        obj.setAuthorizedOfficialFirstName(authorizedOfficialFirstName);
    }

	public void setAuthorizedOfficialLastName(OrganizationalProvider obj, int index) {
        String authorizedOfficialLastName = "authorizedOfficialLastName_" + index;
        if (authorizedOfficialLastName.length() > 30) {
            authorizedOfficialLastName = authorizedOfficialLastName.substring(0, 30);
        }
        obj.setAuthorizedOfficialLastName(authorizedOfficialLastName);
    }

	public void setAuthorizedOfficialNamePrefix(OrganizationalProvider obj, int index) {
        String authorizedOfficialNamePrefix = "authorizedOfficialNamePrefix_" + index;
        if (authorizedOfficialNamePrefix.length() > 30) {
            authorizedOfficialNamePrefix = authorizedOfficialNamePrefix.substring(0, 30);
        }
        obj.setAuthorizedOfficialNamePrefix(authorizedOfficialNamePrefix);
    }

	public void setAuthorizedOfficialTelephoneNumber(OrganizationalProvider obj, int index) {
        String authorizedOfficialTelephoneNumber = "authorizedOfficialTelephoneN_" + index;
        if (authorizedOfficialTelephoneNumber.length() > 30) {
            authorizedOfficialTelephoneNumber = authorizedOfficialTelephoneNumber.substring(0, 30);
        }
        obj.setAuthorizedOfficialTelephoneNumber(authorizedOfficialTelephoneNumber);
    }

	public void setAuthorizedOfficialTitle(OrganizationalProvider obj, int index) {
        String authorizedOfficialTitle = "authorizedOfficialTitle_" + index;
        if (authorizedOfficialTitle.length() > 30) {
            authorizedOfficialTitle = authorizedOfficialTitle.substring(0, 30);
        }
        obj.setAuthorizedOfficialTitle(authorizedOfficialTitle);
    }

	public void setEntityType(OrganizationalProvider obj, int index) {
        EntityType entityType = EntityType.class.getEnumConstants()[0];
        obj.setEntityType(entityType);
    }

	public void setEnumerationDate(OrganizationalProvider obj, int index) {
        String enumerationDate = "enumerationDate_" + index;
        if (enumerationDate.length() > 30) {
            enumerationDate = enumerationDate.substring(0, 30);
        }
        obj.setEnumerationDate(enumerationDate);
    }

	public void setFirstLineMailingAddress(OrganizationalProvider obj, int index) {
        String firstLineMailingAddress = "foo" + index + "@bar.com";
        if (firstLineMailingAddress.length() > 30) {
            firstLineMailingAddress = firstLineMailingAddress.substring(0, 30);
        }
        obj.setFirstLineMailingAddress(firstLineMailingAddress);
    }

	public void setFirstLinePracticeLocationAddress(OrganizationalProvider obj, int index) {
        String firstLinePracticeLocationAddress = "firstLinePracticeLocationAdd_" + index;
        if (firstLinePracticeLocationAddress.length() > 30) {
            firstLinePracticeLocationAddress = firstLinePracticeLocationAddress.substring(0, 30);
        }
        obj.setFirstLinePracticeLocationAddress(firstLinePracticeLocationAddress);
    }

	public void setLastUpdateDate(OrganizationalProvider obj, int index) {
        String lastUpdateDate = "lastUpdateDate_" + index;
        if (lastUpdateDate.length() > 30) {
            lastUpdateDate = lastUpdateDate.substring(0, 30);
        }
        obj.setLastUpdateDate(lastUpdateDate);
    }

	public void setMailingAddressCityName(OrganizationalProvider obj, int index) {
        String mailingAddressCityName = "mailingAddressCityName_" + index;
        if (mailingAddressCityName.length() > 30) {
            mailingAddressCityName = mailingAddressCityName.substring(0, 30);
        }
        obj.setMailingAddressCityName(mailingAddressCityName);
    }

	public void setMailingAddressCountryCode(OrganizationalProvider obj, int index) {
        String mailingAddressCountryCode = "mailingAddressCountryCode_" + index;
        if (mailingAddressCountryCode.length() > 30) {
            mailingAddressCountryCode = mailingAddressCountryCode.substring(0, 30);
        }
        obj.setMailingAddressCountryCode(mailingAddressCountryCode);
    }

	public void setMailingAddressFaxNumber(OrganizationalProvider obj, int index) {
        String mailingAddressFaxNumber = "mailingAddressFaxNumber_" + index;
        if (mailingAddressFaxNumber.length() > 30) {
            mailingAddressFaxNumber = mailingAddressFaxNumber.substring(0, 30);
        }
        obj.setMailingAddressFaxNumber(mailingAddressFaxNumber);
    }

	public void setMailingAddressPostalCode(OrganizationalProvider obj, int index) {
        String mailingAddressPostalCode = "mailingAddressPostalCode_" + index;
        if (mailingAddressPostalCode.length() > 30) {
            mailingAddressPostalCode = mailingAddressPostalCode.substring(0, 30);
        }
        obj.setMailingAddressPostalCode(mailingAddressPostalCode);
    }

	public void setMailingAddressStateName(OrganizationalProvider obj, int index) {
        String mailingAddressStateName = "mailingAddressStateName_" + index;
        if (mailingAddressStateName.length() > 30) {
            mailingAddressStateName = mailingAddressStateName.substring(0, 30);
        }
        obj.setMailingAddressStateName(mailingAddressStateName);
    }

	public void setMailingAddressTelephoneNumber(OrganizationalProvider obj, int index) {
        String mailingAddressTelephoneNumber = "mailingAddressTelephoneNumbe_" + index;
        if (mailingAddressTelephoneNumber.length() > 30) {
            mailingAddressTelephoneNumber = mailingAddressTelephoneNumber.substring(0, 30);
        }
        obj.setMailingAddressTelephoneNumber(mailingAddressTelephoneNumber);
    }

	public void setNpi(OrganizationalProvider obj, int index) {
        String npi = "npi_" + index;
        if (npi.length() > 30) {
            npi = npi.substring(0, 30);
        }
        obj.setNpi(npi);
    }

	public void setOrgName(OrganizationalProvider obj, int index) {
        String orgName = "orgName_" + index;
        if (orgName.length() > 30) {
            orgName = orgName.substring(0, 30);
        }
        obj.setOrgName(orgName);
    }

	public void setOtherOrgName(OrganizationalProvider obj, int index) {
        String otherOrgName = "otherOrgName_" + index;
        if (otherOrgName.length() > 30) {
            otherOrgName = otherOrgName.substring(0, 30);
        }
        obj.setOtherOrgName(otherOrgName);
    }

	public void setPracticeLocationAddressCityName(OrganizationalProvider obj, int index) {
        String practiceLocationAddressCityName = "practiceLocationAddressCityN_" + index;
        if (practiceLocationAddressCityName.length() > 30) {
            practiceLocationAddressCityName = practiceLocationAddressCityName.substring(0, 30);
        }
        obj.setPracticeLocationAddressCityName(practiceLocationAddressCityName);
    }

	public void setPracticeLocationAddressCountryCode(OrganizationalProvider obj, int index) {
        String practiceLocationAddressCountryCode = "practiceLocationAddressCount_" + index;
        if (practiceLocationAddressCountryCode.length() > 30) {
            practiceLocationAddressCountryCode = practiceLocationAddressCountryCode.substring(0, 30);
        }
        obj.setPracticeLocationAddressCountryCode(practiceLocationAddressCountryCode);
    }

	public void setPracticeLocationAddressFaxNumber(OrganizationalProvider obj, int index) {
        String practiceLocationAddressFaxNumber = "practiceLocationAddressFaxNu_" + index;
        if (practiceLocationAddressFaxNumber.length() > 30) {
            practiceLocationAddressFaxNumber = practiceLocationAddressFaxNumber.substring(0, 30);
        }
        obj.setPracticeLocationAddressFaxNumber(practiceLocationAddressFaxNumber);
    }

	public void setPracticeLocationAddressPostalCode(OrganizationalProvider obj, int index) {
        String practiceLocationAddressPostalCode = "practiceLocationAddressPosta_" + index;
        if (practiceLocationAddressPostalCode.length() > 30) {
            practiceLocationAddressPostalCode = practiceLocationAddressPostalCode.substring(0, 30);
        }
        obj.setPracticeLocationAddressPostalCode(practiceLocationAddressPostalCode);
    }

	public void setPracticeLocationAddressStateName(OrganizationalProvider obj, int index) {
        String practiceLocationAddressStateName = "practiceLocationAddressState_" + index;
        if (practiceLocationAddressStateName.length() > 30) {
            practiceLocationAddressStateName = practiceLocationAddressStateName.substring(0, 30);
        }
        obj.setPracticeLocationAddressStateName(practiceLocationAddressStateName);
    }

	public void setPracticeLocationAddressTelephoneNumber(OrganizationalProvider obj, int index) {
        String practiceLocationAddressTelephoneNumber = "practiceLocationAddressTelep_" + index;
        if (practiceLocationAddressTelephoneNumber.length() > 30) {
            practiceLocationAddressTelephoneNumber = practiceLocationAddressTelephoneNumber.substring(0, 30);
        }
        obj.setPracticeLocationAddressTelephoneNumber(practiceLocationAddressTelephoneNumber);
    }

	public void setProviderTaxonomyCode(OrganizationalProvider obj, int index) {
        String providerTaxonomyCode = "providerTaxonomyCode_" + index;
        if (providerTaxonomyCode.length() > 30) {
            providerTaxonomyCode = providerTaxonomyCode.substring(0, 30);
        }
        obj.setProviderTaxonomyCode(providerTaxonomyCode);
    }

	public void setProviderTaxonomyDescription(OrganizationalProvider obj, int index) {
        String providerTaxonomyDescription = "providerTaxonomyDescription_" + index;
        if (providerTaxonomyDescription.length() > 30) {
            providerTaxonomyDescription = providerTaxonomyDescription.substring(0, 30);
        }
        obj.setProviderTaxonomyDescription(providerTaxonomyDescription);
    }

	public void setSecondLineMailingAddress(OrganizationalProvider obj, int index) {
        String secondLineMailingAddress = "foo" + index + "@bar.com";
        if (secondLineMailingAddress.length() > 30) {
            secondLineMailingAddress = secondLineMailingAddress.substring(0, 30);
        }
        obj.setSecondLineMailingAddress(secondLineMailingAddress);
    }

	public void setSecondLinePracticeLocationAddress(OrganizationalProvider obj, int index) {
        String secondLinePracticeLocationAddress = "secondLinePracticeLocationAd_" + index;
        if (secondLinePracticeLocationAddress.length() > 30) {
            secondLinePracticeLocationAddress = secondLinePracticeLocationAddress.substring(0, 30);
        }
        obj.setSecondLinePracticeLocationAddress(secondLinePracticeLocationAddress);
    }

	public OrganizationalProvider getSpecificOrganizationalProvider(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        OrganizationalProvider obj = data.get(index);
        Long id = obj.getId();
        return organizationalProviderService.findOrganizationalProvider(id);
    }

	public OrganizationalProvider getRandomOrganizationalProvider() {
        init();
        OrganizationalProvider obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return organizationalProviderService.findOrganizationalProvider(id);
    }

	public boolean modifyOrganizationalProvider(OrganizationalProvider obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = organizationalProviderService.findOrganizationalProviderEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'OrganizationalProvider' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<OrganizationalProvider>();
        for (int i = 0; i < 10; i++) {
            OrganizationalProvider obj = getNewTransientOrganizationalProvider(i);
            try {
                organizationalProviderService.saveOrganizationalProvider(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            organizationalProviderRepository.flush();
            data.add(obj);
        }
    }
}
