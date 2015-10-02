package gov.samhsa.consent2share.domain.clinicaldata;

import gov.samhsa.consent2share.domain.patient.PatientDataOnDemand;
import gov.samhsa.consent2share.domain.reference.ResultInterpretationCodeDataOnDemand;
import gov.samhsa.consent2share.domain.reference.ResultStatusCodeDataOnDemand;
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
public class ResultObservationDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<ResultObservation> data;

	@Autowired
    PatientDataOnDemand patientDataOnDemand;

	@Autowired
    ResultInterpretationCodeDataOnDemand resultInterpretationCodeDataOnDemand;

	@Autowired
    ResultStatusCodeDataOnDemand resultStatusCodeDataOnDemand;

	@Autowired
    ResultObservationRepository resultObservationRepository;

	public ResultObservation getNewTransientResultObservation(int index) {
        ResultObservation obj = new ResultObservation();
        setResultType(obj, index);
        setResultValue(obj, index);
        setResultDateTime(obj, index);
        setResultReferenceRange(obj, index);
        return obj;
    }

	public void setResultType(ResultObservation obj, int index) {
        CodedConcept embeddedClass = new CodedConcept();
        setResultTypeCode(embeddedClass, index);
        setResultTypeCodeSystem(embeddedClass, index);
        setResultTypeDisplayName(embeddedClass, index);
        setResultTypeCodeSystemName(embeddedClass, index);
        setResultTypeOriginalText(embeddedClass, index);
        obj.setResultType(embeddedClass);
    }

	public void setResultTypeCode(CodedConcept obj, int index) {
        String code = "code_" + index;
        if (code.length() > 250) {
            code = code.substring(0, 250);
        }
        obj.setCode(code);
    }

	public void setResultTypeCodeSystem(CodedConcept obj, int index) {
        String codeSystem = "codeSystem_" + index;
        if (codeSystem.length() > 250) {
            codeSystem = codeSystem.substring(0, 250);
        }
        obj.setCodeSystem(codeSystem);
    }

	public void setResultTypeDisplayName(CodedConcept obj, int index) {
        String displayName = "displayName_" + index;
        if (displayName.length() > 250) {
            displayName = displayName.substring(0, 250);
        }
        obj.setDisplayName(displayName);
    }

	public void setResultTypeCodeSystemName(CodedConcept obj, int index) {
        String codeSystemName = "codeSystemName_" + index;
        if (codeSystemName.length() > 250) {
            codeSystemName = codeSystemName.substring(0, 250);
        }
        obj.setCodeSystemName(codeSystemName);
    }

	public void setResultTypeOriginalText(CodedConcept obj, int index) {
        String originalText = "originalText_" + index;
        if (originalText.length() > 250) {
            originalText = originalText.substring(0, 250);
        }
        obj.setOriginalText(originalText);
    }

	public void setResultValue(ResultObservation obj, int index) {
        Quantity embeddedClass = new Quantity();
        setResultValueMeasuredValue(embeddedClass, index);
        setResultValueUnitOfMeasureCode(embeddedClass, index);
        obj.setResultValue(embeddedClass);
    }

	public void setResultValueMeasuredValue(Quantity obj, int index) {
        Double measuredValue = new Integer(index).doubleValue();
        obj.setMeasuredValue(measuredValue);
    }

	public void setResultValueUnitOfMeasureCode(Quantity obj, int index) {
        UnitOfMeasureCode unitOfMeasureCode = null;
        obj.setUnitOfMeasureCode(unitOfMeasureCode);
    }

	public void setResultDateTime(ResultObservation obj, int index) {
        Date resultDateTime = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setResultDateTime(resultDateTime);
    }

	public void setResultReferenceRange(ResultObservation obj, int index) {
        String resultReferenceRange = "resultReferenceRange_" + index;
        obj.setResultReferenceRange(resultReferenceRange);
    }

	public ResultObservation getSpecificResultObservation(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        ResultObservation obj = data.get(index);
        Long id = obj.getId();
        return resultObservationRepository.findOne(id);
    }

	public ResultObservation getRandomResultObservation() {
        init();
        ResultObservation obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return resultObservationRepository.findOne(id);
    }

	public boolean modifyResultObservation(ResultObservation obj) {
        return false;
    }

	public void init() {
		int pageNumber = 0;
        int pageSize = 10;
        data = resultObservationRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'ResultObservation' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<ResultObservation>();
        for (int i = 0; i < 10; i++) {
            ResultObservation obj = getNewTransientResultObservation(i);
            try {
                resultObservationRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            resultObservationRepository.flush();
            data.add(obj);
        }
    }
}
