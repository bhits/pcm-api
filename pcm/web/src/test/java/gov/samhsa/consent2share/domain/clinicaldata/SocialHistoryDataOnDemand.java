package gov.samhsa.consent2share.domain.clinicaldata;

import gov.samhsa.consent2share.domain.patient.PatientDataOnDemand;
import gov.samhsa.consent2share.domain.reference.SocialHistoryStatusCodeDataOnDemand;
import gov.samhsa.consent2share.domain.reference.SocialHistoryTypeCodeDataOnDemand;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Configurable
@Component
public class SocialHistoryDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<SocialHistory> data;

	@Autowired
    PatientDataOnDemand patientDataOnDemand;

	@Autowired
    SocialHistoryStatusCodeDataOnDemand socialHistoryStatusCodeDataOnDemand;

	@Autowired
    SocialHistoryTypeCodeDataOnDemand socialHistoryTypeCodeDataOnDemand;

	@Autowired
    SocialHistoryRepository socialHistoryRepository;

	public SocialHistory getNewTransientSocialHistory(int index) {
        SocialHistory obj = new SocialHistory();
        setSocialHistoryEndDate(obj, index);
        setSocialHistoryFreeText(obj, index);
        setSocialHistoryStartDate(obj, index);
        return obj;
    }

	public void setSocialHistoryEndDate(SocialHistory obj, int index) {
        Date SocialHistoryEndDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setSocialHistoryEndDate(SocialHistoryEndDate);
    }

	public void setSocialHistoryFreeText(SocialHistory obj, int index) {
        String socialHistoryFreeText = "socialHistoryFreeText_" + index;
        obj.setSocialHistoryFreeText(socialHistoryFreeText);
    }

	public void setSocialHistoryStartDate(SocialHistory obj, int index) {
        Date socialHistoryStartDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setSocialHistoryStartDate(socialHistoryStartDate);
    }

	public SocialHistory getSpecificSocialHistory(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        SocialHistory obj = data.get(index);
        Long id = obj.getId();
        return socialHistoryRepository.findOne(id);
    }

	public SocialHistory getRandomSocialHistory() {
        init();
        SocialHistory obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return socialHistoryRepository.findOne(id);
    }

	public boolean modifySocialHistory(SocialHistory obj) {
        return false;
    }

	public void init() {
		int pageNumber = 0;
        int pageSize = 10;
        data = socialHistoryRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'SocialHistory' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<SocialHistory>();
        for (int i = 0; i < 10; i++) {
            SocialHistory obj = getNewTransientSocialHistory(i);
            try {
                socialHistoryRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            socialHistoryRepository.flush();
            data.add(obj);
        }
    }
}
