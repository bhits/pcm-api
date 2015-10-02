package gov.samhsa.consent2share.domain.consent;

import gov.samhsa.consent2share.domain.patient.PatientDataOnDemand;
import gov.samhsa.consent2share.service.consent.ConsentService;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class ConsentDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Consent> data;

	@Autowired
    PatientDataOnDemand patientDataOnDemand;

	@Autowired
    ConsentService consentService;

	@Autowired
    ConsentRepository consentRepository;

	public Consent getNewTransientConsent(int index) {
        Consent obj = new Consent();
        setConsentRevoked(obj, index);
        setContactMeInCaseOfSubpoena(obj, index);
        setDescription(obj, index);
        setName(obj, index);
        setNotifyMeWhenMyRecordIsAccessed(obj, index);
        setRevocationDate(obj, index);
        setSignedDate(obj, index);
        setSignedPdfConsent(obj, index);
        setUnsignedPdfConsent(obj, index);
        return obj;
    }

	public void setConsentRevoked(Consent obj, int index) {
        Boolean consentRevoked = Boolean.TRUE;
        obj.setConsentRevoked(consentRevoked);
    }

	public void setContactMeInCaseOfSubpoena(Consent obj, int index) {
        Boolean contactMeInCaseOfSubpoena = Boolean.TRUE;
        //obj.setContactMeInCaseOfSubpoena(contactMeInCaseOfSubpoena);
    }

	public void setDescription(Consent obj, int index) {
        String description = "description_" + index;
        if (description.length() > 250) {
            description = description.substring(0, 250);
        }
        obj.setDescription(description);
    }

	public void setName(Consent obj, int index) {
        String name = "name_" + index;
        if (name.length() > 30) {
            name = name.substring(0, 30);
        }
        obj.setName(name);
    }

	public void setNotifyMeWhenMyRecordIsAccessed(Consent obj, int index) {
        Boolean notifyMeWhenMyRecordIsAccessed = Boolean.TRUE;
        //obj.setNotifyMeWhenMyRecordIsAccessed(notifyMeWhenMyRecordIsAccessed);
    }

	public void setRevocationDate(Consent obj, int index) {
        Date revocationDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setRevocationDate(revocationDate);
    }

	public void setSignedDate(Consent obj, int index) {
        Date signedDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setSignedDate(signedDate);
    }

	public void setSignedPdfConsent(Consent obj, int index) {
        SignedPDFConsent signedPdfConsent = null;
        obj.setSignedPdfConsent(signedPdfConsent);
    }

	public void setUnsignedPdfConsent(Consent obj, int index) {
        obj.setUnsignedPdfConsent(new byte[]{});
    }

	public Consent getSpecificConsent(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Consent obj = data.get(index);
        Long id = obj.getId();
        return consentService.findConsent(id);
    }

	public Consent getRandomConsent() {
        init();
        Consent obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return consentService.findConsent(id);
    }

	public boolean modifyConsent(Consent obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = consentService.findConsentEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Consent' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Consent>();
        for (int i = 0; i < 10; i++) {
            Consent obj = getNewTransientConsent(i);
            try {
                consentService.saveConsent(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            consentRepository.flush();
            data.add(obj);
        }
    }
}
