package gov.samhsa.consent2share.domain.provider;

import gov.samhsa.consent2share.domain.reference.EntityType;
import gov.samhsa.consent2share.service.provider.IndividualProviderService;
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
public class IndividualProviderDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<IndividualProvider> data;

	@Autowired
    IndividualProviderService individualProviderService;

	@Autowired
    IndividualProviderRepository individualProviderRepository;

	public IndividualProvider getNewTransientIndividualProvider(int index) {
        IndividualProvider obj = new IndividualProvider();
        setCredential(obj, index);
        setEntityType(obj, index);
        setEnumerationDate(obj, index);
        setFirstLineMailingAddress(obj, index);
        setFirstLinePracticeLocationAddress(obj, index);
        setFirstName(obj, index);
        setLastName(obj, index);
        setLastUpdateDate(obj, index);
        setMailingAddressCityName(obj, index);
        setMailingAddressCountryCode(obj, index);
        setMailingAddressFaxNumber(obj, index);
        setMailingAddressPostalCode(obj, index);
        setMailingAddressStateName(obj, index);
        setMailingAddressTelephoneNumber(obj, index);
        setMiddleName(obj, index);
        setNamePrefix(obj, index);
        setNameSuffix(obj, index);
        setNpi(obj, index);
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

	public void setCredential(IndividualProvider obj, int index) {
        String credential = "credential_" + index;
        if (credential.length() > 30) {
            credential = credential.substring(0, 30);
        }
        obj.setCredential(credential);
    }

	public void setEntityType(IndividualProvider obj, int index) {
        EntityType entityType = EntityType.class.getEnumConstants()[0];
        obj.setEntityType(entityType);
    }

	public void setEnumerationDate(IndividualProvider obj, int index) {
        String enumerationDate = "enumerationDate_" + index;
        if (enumerationDate.length() > 30) {
            enumerationDate = enumerationDate.substring(0, 30);
        }
        obj.setEnumerationDate(enumerationDate);
    }

	public void setFirstLineMailingAddress(IndividualProvider obj, int index) {
        String firstLineMailingAddress = "foo" + index + "@bar.com";
        if (firstLineMailingAddress.length() > 30) {
            firstLineMailingAddress = firstLineMailingAddress.substring(0, 30);
        }
        obj.setFirstLineMailingAddress(firstLineMailingAddress);
    }

	public void setFirstLinePracticeLocationAddress(IndividualProvider obj, int index) {
        String firstLinePracticeLocationAddress = "firstLinePracticeLocationAdd_" + index;
        if (firstLinePracticeLocationAddress.length() > 30) {
            firstLinePracticeLocationAddress = firstLinePracticeLocationAddress.substring(0, 30);
        }
        obj.setFirstLinePracticeLocationAddress(firstLinePracticeLocationAddress);
    }

	public void setFirstName(IndividualProvider obj, int index) {
        String firstName = "firstName_" + index;
        if (firstName.length() > 30) {
            firstName = firstName.substring(0, 30);
        }
        obj.setFirstName(firstName);
    }

	public void setLastName(IndividualProvider obj, int index) {
        String lastName = "lastName_" + index;
        if (lastName.length() > 30) {
            lastName = lastName.substring(0, 30);
        }
        obj.setLastName(lastName);
    }

	public void setLastUpdateDate(IndividualProvider obj, int index) {
        String lastUpdateDate = "lastUpdateDate_" + index;
        if (lastUpdateDate.length() > 30) {
            lastUpdateDate = lastUpdateDate.substring(0, 30);
        }
        obj.setLastUpdateDate(lastUpdateDate);
    }

	public void setMailingAddressCityName(IndividualProvider obj, int index) {
        String mailingAddressCityName = "mailingAddressCityName_" + index;
        if (mailingAddressCityName.length() > 30) {
            mailingAddressCityName = mailingAddressCityName.substring(0, 30);
        }
        obj.setMailingAddressCityName(mailingAddressCityName);
    }

	public void setMailingAddressCountryCode(IndividualProvider obj, int index) {
        String mailingAddressCountryCode = "mailingAddressCountryCode_" + index;
        if (mailingAddressCountryCode.length() > 30) {
            mailingAddressCountryCode = mailingAddressCountryCode.substring(0, 30);
        }
        obj.setMailingAddressCountryCode(mailingAddressCountryCode);
    }

	public void setMailingAddressFaxNumber(IndividualProvider obj, int index) {
        String mailingAddressFaxNumber = "mailingAddressFaxNumber_" + index;
        if (mailingAddressFaxNumber.length() > 30) {
            mailingAddressFaxNumber = mailingAddressFaxNumber.substring(0, 30);
        }
        obj.setMailingAddressFaxNumber(mailingAddressFaxNumber);
    }

	public void setMailingAddressPostalCode(IndividualProvider obj, int index) {
        String mailingAddressPostalCode = "mailingAddressPostalCode_" + index;
        if (mailingAddressPostalCode.length() > 30) {
            mailingAddressPostalCode = mailingAddressPostalCode.substring(0, 30);
        }
        obj.setMailingAddressPostalCode(mailingAddressPostalCode);
    }

	public void setMailingAddressStateName(IndividualProvider obj, int index) {
        String mailingAddressStateName = "mailingAddressStateName_" + index;
        if (mailingAddressStateName.length() > 30) {
            mailingAddressStateName = mailingAddressStateName.substring(0, 30);
        }
        obj.setMailingAddressStateName(mailingAddressStateName);
    }

	public void setMailingAddressTelephoneNumber(IndividualProvider obj, int index) {
        String mailingAddressTelephoneNumber = "mailingAddressTelephoneNumbe_" + index;
        if (mailingAddressTelephoneNumber.length() > 30) {
            mailingAddressTelephoneNumber = mailingAddressTelephoneNumber.substring(0, 30);
        }
        obj.setMailingAddressTelephoneNumber(mailingAddressTelephoneNumber);
    }

	public void setMiddleName(IndividualProvider obj, int index) {
        String middleName = "middleName_" + index;
        if (middleName.length() > 30) {
            middleName = middleName.substring(0, 30);
        }
        obj.setMiddleName(middleName);
    }

	public void setNamePrefix(IndividualProvider obj, int index) {
        String namePrefix = "namePrefix_" + index;
        if (namePrefix.length() > 30) {
            namePrefix = namePrefix.substring(0, 30);
        }
        obj.setNamePrefix(namePrefix);
    }

	public void setNameSuffix(IndividualProvider obj, int index) {
        String nameSuffix = "nameSuffix_" + index;
        if (nameSuffix.length() > 30) {
            nameSuffix = nameSuffix.substring(0, 30);
        }
        obj.setNameSuffix(nameSuffix);
    }

	public void setNpi(IndividualProvider obj, int index) {
        String npi = "npi_" + index;
        if (npi.length() > 30) {
            npi = npi.substring(0, 30);
        }
        obj.setNpi(npi);
    }

	public void setPracticeLocationAddressCityName(IndividualProvider obj, int index) {
        String practiceLocationAddressCityName = "practiceLocationAddressCityN_" + index;
        if (practiceLocationAddressCityName.length() > 30) {
            practiceLocationAddressCityName = practiceLocationAddressCityName.substring(0, 30);
        }
        obj.setPracticeLocationAddressCityName(practiceLocationAddressCityName);
    }

	public void setPracticeLocationAddressCountryCode(IndividualProvider obj, int index) {
        String practiceLocationAddressCountryCode = "practiceLocationAddressCount_" + index;
        if (practiceLocationAddressCountryCode.length() > 30) {
            practiceLocationAddressCountryCode = practiceLocationAddressCountryCode.substring(0, 30);
        }
        obj.setPracticeLocationAddressCountryCode(practiceLocationAddressCountryCode);
    }

	public void setPracticeLocationAddressFaxNumber(IndividualProvider obj, int index) {
        String practiceLocationAddressFaxNumber = "practiceLocationAddressFaxNu_" + index;
        if (practiceLocationAddressFaxNumber.length() > 30) {
            practiceLocationAddressFaxNumber = practiceLocationAddressFaxNumber.substring(0, 30);
        }
        obj.setPracticeLocationAddressFaxNumber(practiceLocationAddressFaxNumber);
    }

	public void setPracticeLocationAddressPostalCode(IndividualProvider obj, int index) {
        String practiceLocationAddressPostalCode = "practiceLocationAddressPosta_" + index;
        if (practiceLocationAddressPostalCode.length() > 30) {
            practiceLocationAddressPostalCode = practiceLocationAddressPostalCode.substring(0, 30);
        }
        obj.setPracticeLocationAddressPostalCode(practiceLocationAddressPostalCode);
    }

	public void setPracticeLocationAddressStateName(IndividualProvider obj, int index) {
        String practiceLocationAddressStateName = "practiceLocationAddressState_" + index;
        if (practiceLocationAddressStateName.length() > 30) {
            practiceLocationAddressStateName = practiceLocationAddressStateName.substring(0, 30);
        }
        obj.setPracticeLocationAddressStateName(practiceLocationAddressStateName);
    }

	public void setPracticeLocationAddressTelephoneNumber(IndividualProvider obj, int index) {
        String practiceLocationAddressTelephoneNumber = "practiceLocationAddressTelep_" + index;
        if (practiceLocationAddressTelephoneNumber.length() > 30) {
            practiceLocationAddressTelephoneNumber = practiceLocationAddressTelephoneNumber.substring(0, 30);
        }
        obj.setPracticeLocationAddressTelephoneNumber(practiceLocationAddressTelephoneNumber);
    }

	public void setProviderTaxonomyCode(IndividualProvider obj, int index) {
        String providerTaxonomyCode = "providerTaxonomyCode_" + index;
        if (providerTaxonomyCode.length() > 30) {
            providerTaxonomyCode = providerTaxonomyCode.substring(0, 30);
        }
        obj.setProviderTaxonomyCode(providerTaxonomyCode);
    }

	public void setProviderTaxonomyDescription(IndividualProvider obj, int index) {
        String providerTaxonomyDescription = "providerTaxonomyDescription_" + index;
        if (providerTaxonomyDescription.length() > 30) {
            providerTaxonomyDescription = providerTaxonomyDescription.substring(0, 30);
        }
        obj.setProviderTaxonomyDescription(providerTaxonomyDescription);
    }

	public void setSecondLineMailingAddress(IndividualProvider obj, int index) {
        String secondLineMailingAddress = "foo" + index + "@bar.com";
        if (secondLineMailingAddress.length() > 30) {
            secondLineMailingAddress = secondLineMailingAddress.substring(0, 30);
        }
        obj.setSecondLineMailingAddress(secondLineMailingAddress);
    }

	public void setSecondLinePracticeLocationAddress(IndividualProvider obj, int index) {
        String secondLinePracticeLocationAddress = "secondLinePracticeLocationAd_" + index;
        if (secondLinePracticeLocationAddress.length() > 30) {
            secondLinePracticeLocationAddress = secondLinePracticeLocationAddress.substring(0, 30);
        }
        obj.setSecondLinePracticeLocationAddress(secondLinePracticeLocationAddress);
    }

	public IndividualProvider getSpecificIndividualProvider(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        IndividualProvider obj = data.get(index);
        Long id = obj.getId();
        return individualProviderService.findIndividualProvider(id);
    }

	public IndividualProvider getRandomIndividualProvider() {
        init();
        IndividualProvider obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return individualProviderService.findIndividualProvider(id);
    }

	public boolean modifyIndividualProvider(IndividualProvider obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = individualProviderService.findIndividualProviderEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'IndividualProvider' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<IndividualProvider>();
        for (int i = 0; i < 10; i++) {
            IndividualProvider obj = getNewTransientIndividualProvider(i);
            try {
                individualProviderService.saveIndividualProvider(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            individualProviderRepository.flush();
            data.add(obj);
        }
    }
}
