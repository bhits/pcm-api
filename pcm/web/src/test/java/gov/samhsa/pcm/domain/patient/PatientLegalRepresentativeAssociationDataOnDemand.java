package gov.samhsa.pcm.domain.patient;

import gov.samhsa.pcm.domain.reference.LegalRepresentativeTypeCodeDataOnDemand;
import gov.samhsa.pcm.service.patient.PatientLegalRepresentativeAssociationService;

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

@Configurable
@Component
public class PatientLegalRepresentativeAssociationDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<PatientLegalRepresentativeAssociation> data;

	@Autowired
    LegalRepresentativeTypeCodeDataOnDemand legalRepresentativeTypeCodeDataOnDemand;

	@Autowired
    PatientLegalRepresentativeAssociationService patientLegalRepresentativeAssociationService;

	@Autowired
    PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository;

	public PatientLegalRepresentativeAssociation getNewTransientPatientLegalRepresentativeAssociation(int index) {
        PatientLegalRepresentativeAssociation obj = new PatientLegalRepresentativeAssociation();
        setPatientLegalRepresentativeAssociationPk(obj, index);
        setRelationshipEndDate(obj, index);
        setRelationshipStartDate(obj, index);
        return obj;
    }

	public void setPatientLegalRepresentativeAssociationPk(PatientLegalRepresentativeAssociation obj, int index) {
        PatientLegalRepresentativeAssociationPk embeddedClass = new PatientLegalRepresentativeAssociationPk();
        setPatientLegalRepresentativeAssociationPkPatient(embeddedClass, index);
        setPatientLegalRepresentativeAssociationPkLegalRepresentative(embeddedClass, index);
        obj.setPatientLegalRepresentativeAssociationPk(embeddedClass);
    }

	public void setPatientLegalRepresentativeAssociationPkPatient(PatientLegalRepresentativeAssociationPk obj, int index) {
        Patient patient = null;
        obj.setPatient(patient);
    }

	public void setPatientLegalRepresentativeAssociationPkLegalRepresentative(PatientLegalRepresentativeAssociationPk obj, int index) {
        Patient legalRepresentative = null;
        obj.setLegalRepresentative(legalRepresentative);
    }

	public void setRelationshipEndDate(PatientLegalRepresentativeAssociation obj, int index) {
        Date relationshipEndDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setRelationshipEndDate(relationshipEndDate);
    }

	public void setRelationshipStartDate(PatientLegalRepresentativeAssociation obj, int index) {
        Date relationshipStartDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setRelationshipStartDate(relationshipStartDate);
    }

	public PatientLegalRepresentativeAssociation getSpecificPatientLegalRepresentativeAssociation(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        PatientLegalRepresentativeAssociation obj = data.get(index);
        Long id = obj.getId();
        return patientLegalRepresentativeAssociationService.findPatientLegalRepresentativeAssociation(id);
    }

	public PatientLegalRepresentativeAssociation getRandomPatientLegalRepresentativeAssociation() {
        init();
        PatientLegalRepresentativeAssociation obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return patientLegalRepresentativeAssociationService.findPatientLegalRepresentativeAssociation(id);
    }

	public boolean modifyPatientLegalRepresentativeAssociation(PatientLegalRepresentativeAssociation obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = patientLegalRepresentativeAssociationService.findPatientLegalRepresentativeAssociationEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'PatientLegalRepresentativeAssociation' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<PatientLegalRepresentativeAssociation>();
        for (int i = 0; i < 10; i++) {
            PatientLegalRepresentativeAssociation obj = getNewTransientPatientLegalRepresentativeAssociation(i);
            try {
                patientLegalRepresentativeAssociationService.savePatientLegalRepresentativeAssociation(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            patientLegalRepresentativeAssociationRepository.flush();
            data.add(obj);
        }
    }
}
