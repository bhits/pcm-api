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

@Component
@Configurable
public class PrivacyLawPolicyCodeDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<PrivacyLawPolicyCode> data;

	@Autowired
    PrivacyLawPolicyCodeRepository privacyLawPolicyCodeRepository;

	public PrivacyLawPolicyCode getNewTransientPrivacyLawPolicyCode(int index) {
        PrivacyLawPolicyCode obj = new PrivacyLawPolicyCode();
        setCode(obj, index);
        setCodeSystem(obj, index);
        setCodeSystemName(obj, index);
        setDisplayName(obj, index);
        setOriginalText(obj, index);
        return obj;
    }

	public void setCode(PrivacyLawPolicyCode obj, int index) {
        String code = "code_" + index;
        if (code.length() > 250) {
            code = code.substring(0, 250);
        }
        obj.setCode(code);
    }

	public void setCodeSystem(PrivacyLawPolicyCode obj, int index) {
        String codeSystem = "codeSystem_" + index;
        if (codeSystem.length() > 250) {
            codeSystem = codeSystem.substring(0, 250);
        }
        obj.setCodeSystem(codeSystem);
    }

	public void setCodeSystemName(PrivacyLawPolicyCode obj, int index) {
        String codeSystemName = "codeSystemName_" + index;
        if (codeSystemName.length() > 250) {
            codeSystemName = codeSystemName.substring(0, 250);
        }
        obj.setCodeSystemName(codeSystemName);
    }

	public void setDisplayName(PrivacyLawPolicyCode obj, int index) {
        String displayName = "displayName_" + index;
        if (displayName.length() > 250) {
            displayName = displayName.substring(0, 250);
        }
        obj.setDisplayName(displayName);
    }

	public void setOriginalText(PrivacyLawPolicyCode obj, int index) {
        String originalText = "originalText_" + index;
        if (originalText.length() > 250) {
            originalText = originalText.substring(0, 250);
        }
        obj.setOriginalText(originalText);
    }

	public PrivacyLawPolicyCode getSpecificPrivacyLawPolicyCode(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        PrivacyLawPolicyCode obj = data.get(index);
        Long id = obj.getId();
        return privacyLawPolicyCodeRepository.findOne(id);
    }

	public PrivacyLawPolicyCode getRandomPrivacyLawPolicyCode() {
        init();
        PrivacyLawPolicyCode obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return privacyLawPolicyCodeRepository.findOne(id);
    }

	public boolean modifyPrivacyLawPolicyCode(PrivacyLawPolicyCode obj) {
        return false;
    }

	public void init() {
		int pageNumber = 0;
        int pageSize = 10;
        data = privacyLawPolicyCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'PrivacyLawPolicyCode' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<PrivacyLawPolicyCode>();
        for (int i = 0; i < 10; i++) {
            PrivacyLawPolicyCode obj = getNewTransientPrivacyLawPolicyCode(i);
            try {
            	privacyLawPolicyCodeRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            privacyLawPolicyCodeRepository.flush();
            data.add(obj);
        }
    }
}
