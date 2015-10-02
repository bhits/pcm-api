package gov.samhsa.consent2share.domain.clinicaldata;

import gov.samhsa.consent2share.domain.patient.PatientDataOnDemand;
import gov.samhsa.consent2share.domain.reference.BodySiteCodeDataOnDemand;
import gov.samhsa.consent2share.domain.reference.MedicationStatusCodeDataOnDemand;
import gov.samhsa.consent2share.domain.reference.ProductFormCodeDataOnDemand;
import gov.samhsa.consent2share.domain.reference.RouteCodeDataOnDemand;
import gov.samhsa.consent2share.domain.reference.UnitOfMeasureCode;
import gov.samhsa.consent2share.domain.valueobject.CodedConcept;
import gov.samhsa.consent2share.domain.valueobject.Quantity;

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
public class MedicationDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Medication> data;

	@Autowired
    BodySiteCodeDataOnDemand bodySiteCodeDataOnDemand;

	@Autowired
    MedicationStatusCodeDataOnDemand medicationStatusCodeDataOnDemand;

	@Autowired
    PatientDataOnDemand patientDataOnDemand;

	@Autowired
    ProductFormCodeDataOnDemand productFormCodeDataOnDemand;

	@Autowired
    RouteCodeDataOnDemand routeCodeDataOnDemand;

	@Autowired
    MedicationRepository medicationRepository;

	public Medication getNewTransientMedication(int index) {
        Medication obj = new Medication();
        setMedicationInformationCode(obj, index);
        setDoseQuantity(obj, index);
        setFreeTextSig(obj, index);
        setMedicationEndDate(obj, index);
        setMedicationStartDate(obj, index);
        return obj;
    }

	public void setMedicationInformationCode(Medication obj, int index) {
        CodedConcept embeddedClass = new CodedConcept();
        setMedicationInformationCodeCode(embeddedClass, index);
        setMedicationInformationCodeCodeSystem(embeddedClass, index);
        setMedicationInformationCodeDisplayName(embeddedClass, index);
        setMedicationInformationCodeCodeSystemName(embeddedClass, index);
        setMedicationInformationCodeOriginalText(embeddedClass, index);
        obj.setMedicationInformationCode(embeddedClass);
    }

	public void setMedicationInformationCodeCode(CodedConcept obj, int index) {
        String code = "code_" + index;
        if (code.length() > 250) {
            code = code.substring(0, 250);
        }
        obj.setCode(code);
    }

	public void setMedicationInformationCodeCodeSystem(CodedConcept obj, int index) {
        String codeSystem = "codeSystem_" + index;
        if (codeSystem.length() > 250) {
            codeSystem = codeSystem.substring(0, 250);
        }
        obj.setCodeSystem(codeSystem);
    }

	public void setMedicationInformationCodeDisplayName(CodedConcept obj, int index) {
        String displayName = "displayName_" + index;
        if (displayName.length() > 250) {
            displayName = displayName.substring(0, 250);
        }
        obj.setDisplayName(displayName);
    }

	public void setMedicationInformationCodeCodeSystemName(CodedConcept obj, int index) {
        String codeSystemName = "codeSystemName_" + index;
        if (codeSystemName.length() > 250) {
            codeSystemName = codeSystemName.substring(0, 250);
        }
        obj.setCodeSystemName(codeSystemName);
    }

	public void setMedicationInformationCodeOriginalText(CodedConcept obj, int index) {
        String originalText = "originalText_" + index;
        if (originalText.length() > 250) {
            originalText = originalText.substring(0, 250);
        }
        obj.setOriginalText(originalText);
    }

	public void setDoseQuantity(Medication obj, int index) {
        Quantity embeddedClass = new Quantity();
        setDoseQuantityMeasuredValue(embeddedClass, index);
        setDoseQuantityUnitOfMeasureCode(embeddedClass, index);
        obj.setDoseQuantity(embeddedClass);
    }

	public void setDoseQuantityMeasuredValue(Quantity obj, int index) {
        Double measuredValue = new Integer(index).doubleValue();
        obj.setMeasuredValue(measuredValue);
    }

	public void setDoseQuantityUnitOfMeasureCode(Quantity obj, int index) {
        UnitOfMeasureCode unitOfMeasureCode = null;
        obj.setUnitOfMeasureCode(unitOfMeasureCode);
    }

	public void setFreeTextSig(Medication obj, int index) {
        String freeTextSig = "freeTextSig_" + index;
        if (freeTextSig.length() > 250) {
            freeTextSig = freeTextSig.substring(0, 250);
        }
        obj.setFreeTextSig(freeTextSig);
    }

	public void setMedicationEndDate(Medication obj, int index) {
        Date medicationEndDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setMedicationEndDate(medicationEndDate);
    }

	public void setMedicationStartDate(Medication obj, int index) {
        Date medicationStartDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setMedicationStartDate(medicationStartDate);
    }

	public Medication getSpecificMedication(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Medication obj = data.get(index);
        Long id = obj.getId();
        return medicationRepository.findOne(id);
    }

	public Medication getRandomMedication() {
        init();
        Medication obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return medicationRepository.findOne(id);
    }

	public boolean modifyMedication(Medication obj) {
        return false;
    }

	public void init() {
		int pageNumber = 0;
        int pageSize = 10;
        data = medicationRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Medication' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Medication>();
        for (int i = 0; i < 10; i++) {
            Medication obj = getNewTransientMedication(i);
            try {
                medicationRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            medicationRepository.flush();
            data.add(obj);
        }
    }
}
