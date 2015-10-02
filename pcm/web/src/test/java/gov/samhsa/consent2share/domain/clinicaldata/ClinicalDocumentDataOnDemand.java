package gov.samhsa.consent2share.domain.clinicaldata;

import gov.samhsa.consent2share.domain.patient.PatientDataOnDemand;
import gov.samhsa.consent2share.domain.reference.ClinicalDocumentTypeCodeDataOnDemand;
import gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService;
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
public class ClinicalDocumentDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<ClinicalDocument> data;

	@Autowired
    ClinicalDocumentTypeCodeDataOnDemand clinicalDocumentTypeCodeDataOnDemand;

	@Autowired
    PatientDataOnDemand patientDataOnDemand;

	@Autowired
    ClinicalDocumentService clinicalDocumentService;

	@Autowired
    ClinicalDocumentRepository clinicalDocumentRepository;

	public ClinicalDocument getNewTransientClinicalDocument(int index) {
        ClinicalDocument obj = new ClinicalDocument();
        setContent(obj, index);
        setContentType(obj, index);
        setDescription(obj, index);
        setDocumentSize(obj, index);
        setDocumentUrl(obj, index);
        setFilename(obj, index);
        setName(obj, index);
        return obj;
    }

	public void setContent(ClinicalDocument obj, int index) {
        byte[] content = new byte[]{};
        obj.setContent(content);
    }

	public void setContentType(ClinicalDocument obj, int index) {
        String contentType = "contentType_" + index;
        obj.setContentType(contentType);
    }

	public void setDescription(ClinicalDocument obj, int index) {
        String description = "description_" + index;
        if (description.length() > 500) {
            description = description.substring(0, 500);
        }
        obj.setDescription(description);
    }

	public void setDocumentSize(ClinicalDocument obj, int index) {
        Long documentSize = new Integer(index).longValue();
        obj.setDocumentSize(documentSize);
    }

	public void setDocumentUrl(ClinicalDocument obj, int index) {
        String documentUrl = "documentUrl_" + index;
        if (documentUrl.length() > 100) {
            documentUrl = documentUrl.substring(0, 100);
        }
        obj.setDocumentUrl(documentUrl);
    }

	public void setFilename(ClinicalDocument obj, int index) {
        String filename = "filename_" + index;
        obj.setFilename(filename);
    }

	public void setName(ClinicalDocument obj, int index) {
        String name = "name_" + index;
        if (name.length() > 30) {
            name = name.substring(0, 30);
        }
        obj.setName(name);
    }

	public ClinicalDocument getSpecificClinicalDocument(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        ClinicalDocument obj = data.get(index);
        Long id = obj.getId();
        return clinicalDocumentService.findClinicalDocument(id);
    }

	public ClinicalDocument getRandomClinicalDocument() {
        init();
        ClinicalDocument obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return clinicalDocumentService.findClinicalDocument(id);
    }

	public boolean modifyClinicalDocument(ClinicalDocument obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = clinicalDocumentService.findClinicalDocumentEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'ClinicalDocument' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<ClinicalDocument>();
        for (int i = 0; i < 10; i++) {
            ClinicalDocument obj = getNewTransientClinicalDocument(i);
            try {
                clinicalDocumentRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            clinicalDocumentRepository.flush();
            data.add(obj);
        }
    }
}
