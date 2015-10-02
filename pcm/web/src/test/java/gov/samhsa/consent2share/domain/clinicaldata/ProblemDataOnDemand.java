package gov.samhsa.consent2share.domain.clinicaldata;

import gov.samhsa.consent2share.domain.patient.PatientDataOnDemand;
import gov.samhsa.consent2share.domain.reference.ProblemStatusCodeDataOnDemand;
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
public class ProblemDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Problem> data;

	@Autowired
    PatientDataOnDemand patientDataOnDemand;

	@Autowired
    ProblemStatusCodeDataOnDemand problemStatusCodeDataOnDemand;

	@Autowired
    ProblemRepository problemRepository;

	public Problem getNewTransientProblem(int index) {
        Problem obj = new Problem();
        setProblemCode(obj, index);
        setAgeAtOnSet(obj, index);
        setProblemEndDate(obj, index);
        setProblemStartDate(obj, index);
        return obj;
    }

	public void setProblemCode(Problem obj, int index) {
        CodedConcept embeddedClass = new CodedConcept();
        setProblemCodeCode(embeddedClass, index);
        setProblemCodeCodeSystem(embeddedClass, index);
        setProblemCodeDisplayName(embeddedClass, index);
        setProblemCodeCodeSystemName(embeddedClass, index);
        setProblemCodeOriginalText(embeddedClass, index);
        obj.setProblemCode(embeddedClass);
    }

	public void setProblemCodeCode(CodedConcept obj, int index) {
        String code = "code_" + index;
        if (code.length() > 250) {
            code = code.substring(0, 250);
        }
        obj.setCode(code);
    }

	public void setProblemCodeCodeSystem(CodedConcept obj, int index) {
        String codeSystem = "codeSystem_" + index;
        if (codeSystem.length() > 250) {
            codeSystem = codeSystem.substring(0, 250);
        }
        obj.setCodeSystem(codeSystem);
    }

	public void setProblemCodeDisplayName(CodedConcept obj, int index) {
        String displayName = "displayName_" + index;
        if (displayName.length() > 250) {
            displayName = displayName.substring(0, 250);
        }
        obj.setDisplayName(displayName);
    }

	public void setProblemCodeCodeSystemName(CodedConcept obj, int index) {
        String codeSystemName = "codeSystemName_" + index;
        if (codeSystemName.length() > 250) {
            codeSystemName = codeSystemName.substring(0, 250);
        }
        obj.setCodeSystemName(codeSystemName);
    }

	public void setProblemCodeOriginalText(CodedConcept obj, int index) {
        String originalText = "originalText_" + index;
        if (originalText.length() > 250) {
            originalText = originalText.substring(0, 250);
        }
        obj.setOriginalText(originalText);
    }

	public void setAgeAtOnSet(Problem obj, int index) {
        Integer ageAtOnSet = new Integer(index);
        obj.setAgeAtOnSet(ageAtOnSet);
    }

	public void setProblemEndDate(Problem obj, int index) {
        Date problemEndDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setProblemEndDate(problemEndDate);
    }

	public void setProblemStartDate(Problem obj, int index) {
        Date problemStartDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setProblemStartDate(problemStartDate);
    }

	public Problem getSpecificProblem(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Problem obj = data.get(index);
        Long id = obj.getId();
        return problemRepository.findOne(id);
    }

	public Problem getRandomProblem() {
        init();
        Problem obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return problemRepository.findOne(id);
    }

	public boolean modifyProblem(Problem obj) {
        return false;
    }

	public void init() {
		int pageNumber = 0;
        int pageSize = 10;
        data = problemRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Problem' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Problem>();
        for (int i = 0; i < 10; i++) {
            Problem obj = getNewTransientProblem(i);
            try {
                problemRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            problemRepository.flush();
            data.add(obj);
        }
    }
}
