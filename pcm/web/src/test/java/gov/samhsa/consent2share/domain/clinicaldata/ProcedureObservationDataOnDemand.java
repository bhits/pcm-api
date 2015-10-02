package gov.samhsa.consent2share.domain.clinicaldata;

import gov.samhsa.consent2share.domain.patient.PatientDataOnDemand;
import gov.samhsa.consent2share.domain.reference.ProcedureStatusCodeDataOnDemand;
import gov.samhsa.consent2share.domain.reference.TargetSiteCodeDataOnDemand;
import gov.samhsa.consent2share.domain.valueobject.CodedConcept;

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

@Component
@Configurable
public class ProcedureObservationDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<ProcedureObservation> data;

	@Autowired
    PatientDataOnDemand patientDataOnDemand;

	@Autowired
    ProcedureStatusCodeDataOnDemand procedureStatusCodeDataOnDemand;

	@Autowired
    TargetSiteCodeDataOnDemand targetSiteCodeDataOnDemand;

	@Autowired
    ProcedureObservationRepository procedureObservationRepository;

	public ProcedureObservation getNewTransientProcedureObservation(int index) {
        ProcedureObservation obj = new ProcedureObservation();
        setProcedureType(obj, index);
        setProcedureEndDate(obj, index);
        setProcedureStartDate(obj, index);
        return obj;
    }

	public void setProcedureType(ProcedureObservation obj, int index) {
        CodedConcept embeddedClass = new CodedConcept();
        setProcedureTypeCode(embeddedClass, index);
        setProcedureTypeCodeSystem(embeddedClass, index);
        setProcedureTypeDisplayName(embeddedClass, index);
        setProcedureTypeCodeSystemName(embeddedClass, index);
        setProcedureTypeOriginalText(embeddedClass, index);
        obj.setProcedureType(embeddedClass);
    }

	public void setProcedureTypeCode(CodedConcept obj, int index) {
        String code = "code_" + index;
        if (code.length() > 250) {
            code = code.substring(0, 250);
        }
        obj.setCode(code);
    }

	public void setProcedureTypeCodeSystem(CodedConcept obj, int index) {
        String codeSystem = "codeSystem_" + index;
        if (codeSystem.length() > 250) {
            codeSystem = codeSystem.substring(0, 250);
        }
        obj.setCodeSystem(codeSystem);
    }

	public void setProcedureTypeDisplayName(CodedConcept obj, int index) {
        String displayName = "displayName_" + index;
        if (displayName.length() > 250) {
            displayName = displayName.substring(0, 250);
        }
        obj.setDisplayName(displayName);
    }

	public void setProcedureTypeCodeSystemName(CodedConcept obj, int index) {
        String codeSystemName = "codeSystemName_" + index;
        if (codeSystemName.length() > 250) {
            codeSystemName = codeSystemName.substring(0, 250);
        }
        obj.setCodeSystemName(codeSystemName);
    }

	public void setProcedureTypeOriginalText(CodedConcept obj, int index) {
        String originalText = "originalText_" + index;
        if (originalText.length() > 250) {
            originalText = originalText.substring(0, 250);
        }
        obj.setOriginalText(originalText);
    }

	public void setProcedureEndDate(ProcedureObservation obj, int index) {
        Date procedureEndDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setProcedureEndDate(procedureEndDate);
    }

	public void setProcedureStartDate(ProcedureObservation obj, int index) {
        Date procedureStartDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setProcedureStartDate(procedureStartDate);
    }

	public ProcedureObservation getSpecificProcedureObservation(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        ProcedureObservation obj = data.get(index);
        Long id = obj.getId();
        return procedureObservationRepository.findOne(id);
    }

	public ProcedureObservation getRandomProcedureObservation() {
        init();
        ProcedureObservation obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return procedureObservationRepository.findOne(id);
    }

	public boolean modifyProcedureObservation(ProcedureObservation obj) {
        return false;
    }

	public void init() {
		int pageNumber = 0;
        int pageSize = 10;
        data = procedureObservationRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'ProcedureObservation' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<ProcedureObservation>();
        for (int i = 0; i < 10; i++) {
            ProcedureObservation obj = getNewTransientProcedureObservation(i);
            try {
                procedureObservationRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            procedureObservationRepository.flush();
            data.add(obj);
        }
    }
}
