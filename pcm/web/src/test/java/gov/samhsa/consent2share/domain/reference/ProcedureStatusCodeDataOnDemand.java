package gov.samhsa.consent2share.domain.reference;

import java.security.SecureRandom;
import java.util.ArrayList;
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
public class ProcedureStatusCodeDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<ProcedureStatusCode> data;

	@Autowired
    ProcedureStatusCodeRepository procedureStatusCodeRepository;

	public ProcedureStatusCode getNewTransientProcedureStatusCode(int index) {
        ProcedureStatusCode obj = new ProcedureStatusCode();
        setCode(obj, index);
        setCodeSystem(obj, index);
        setCodeSystemName(obj, index);
        setDisplayName(obj, index);
        setOriginalText(obj, index);
        return obj;
    }

	public void setCode(ProcedureStatusCode obj, int index) {
        String code = "code_" + index;
        if (code.length() > 250) {
            code = code.substring(0, 250);
        }
        obj.setCode(code);
    }

	public void setCodeSystem(ProcedureStatusCode obj, int index) {
        String codeSystem = "codeSystem_" + index;
        if (codeSystem.length() > 250) {
            codeSystem = codeSystem.substring(0, 250);
        }
        obj.setCodeSystem(codeSystem);
    }

	public void setCodeSystemName(ProcedureStatusCode obj, int index) {
        String codeSystemName = "codeSystemName_" + index;
        if (codeSystemName.length() > 250) {
            codeSystemName = codeSystemName.substring(0, 250);
        }
        obj.setCodeSystemName(codeSystemName);
    }

	public void setDisplayName(ProcedureStatusCode obj, int index) {
        String displayName = "displayName_" + index;
        if (displayName.length() > 250) {
            displayName = displayName.substring(0, 250);
        }
        obj.setDisplayName(displayName);
    }

	public void setOriginalText(ProcedureStatusCode obj, int index) {
        String originalText = "originalText_" + index;
        if (originalText.length() > 250) {
            originalText = originalText.substring(0, 250);
        }
        obj.setOriginalText(originalText);
    }

	public ProcedureStatusCode getSpecificProcedureStatusCode(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        ProcedureStatusCode obj = data.get(index);
        Long id = obj.getId();
        return procedureStatusCodeRepository.findOne(id);
    }

	public ProcedureStatusCode getRandomProcedureStatusCode() {
        init();
        ProcedureStatusCode obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return procedureStatusCodeRepository.findOne(id);
    }

	public boolean modifyProcedureStatusCode(ProcedureStatusCode obj) {
        return false;
    }

	public void init() {

int pageNumber = 0;
        int pageSize = 10;
        data = procedureStatusCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'ProcedureStatusCode' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<ProcedureStatusCode>();
        for (int i = 0; i < 10; i++) {
            ProcedureStatusCode obj = getNewTransientProcedureStatusCode(i);
            try {
                procedureStatusCodeRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            procedureStatusCodeRepository.flush();
            data.add(obj);
        }
    }
}
