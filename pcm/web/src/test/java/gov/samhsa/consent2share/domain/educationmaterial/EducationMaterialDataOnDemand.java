package gov.samhsa.consent2share.domain.educationmaterial;

import gov.samhsa.consent2share.service.educationmaterial.EducationMaterialService;
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
public class EducationMaterialDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<EducationMaterial> data;

	@Autowired
    EducationMaterialService educationMaterialService;

	@Autowired
    EducationMaterialRepository educationMaterialRepository;

	public EducationMaterial getNewTransientEducationMaterial(int index) {
        EducationMaterial obj = new EducationMaterial();
        setContent(obj, index);
        setContentType(obj, index);
        setDescription(obj, index);
        setDocumentSize(obj, index);
        setDocumentUrl(obj, index);
        setFilename(obj, index);
        setName(obj, index);
        return obj;
    }

	public void setContent(EducationMaterial obj, int index) {
        String content = "content_" + index;
        if (content.length() > 250) {
            content = content.substring(0, 250);
        }
        obj.setContent(content);
    }

	public void setContentType(EducationMaterial obj, int index) {
        String contentType = "contentType_" + index;
        obj.setContentType(contentType);
    }

	public void setDescription(EducationMaterial obj, int index) {
        String description = "description_" + index;
        if (description.length() > 500) {
            description = description.substring(0, 500);
        }
        obj.setDescription(description);
    }

	public void setDocumentSize(EducationMaterial obj, int index) {
        Long documentSize = new Integer(index).longValue();
        obj.setDocumentSize(documentSize);
    }

	public void setDocumentUrl(EducationMaterial obj, int index) {
        String documentUrl = "documentUrl_" + index;
        if (documentUrl.length() > 250) {
            documentUrl = documentUrl.substring(0, 250);
        }
        obj.setDocumentUrl(documentUrl);
    }

	public void setFilename(EducationMaterial obj, int index) {
        String filename = "filename_" + index;
        obj.setFilename(filename);
    }

	public void setName(EducationMaterial obj, int index) {
        String name = "name_" + index;
        if (name.length() > 30) {
            name = name.substring(0, 30);
        }
        obj.setName(name);
    }

	public EducationMaterial getSpecificEducationMaterial(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        EducationMaterial obj = data.get(index);
        Long id = obj.getId();
        return educationMaterialService.findEducationMaterial(id);
    }

	public EducationMaterial getRandomEducationMaterial() {
        init();
        EducationMaterial obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return educationMaterialService.findEducationMaterial(id);
    }

	public boolean modifyEducationMaterial(EducationMaterial obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = educationMaterialService.findEducationMaterialEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'EducationMaterial' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<EducationMaterial>();
        for (int i = 0; i < 10; i++) {
            EducationMaterial obj = getNewTransientEducationMaterial(i);
            try {
                educationMaterialService.saveEducationMaterial(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            educationMaterialRepository.flush();
            data.add(obj);
        }
    }
}
