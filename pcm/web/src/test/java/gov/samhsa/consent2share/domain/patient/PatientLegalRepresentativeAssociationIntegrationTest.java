package gov.samhsa.consent2share.domain.patient;

import gov.samhsa.consent2share.service.patient.PatientLegalRepresentativeAssociationService;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@ActiveProfiles("test")
@Transactional
public class PatientLegalRepresentativeAssociationIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    PatientLegalRepresentativeAssociationDataOnDemand dod;

	@Autowired
    PatientLegalRepresentativeAssociationService patientLegalRepresentativeAssociationService;

	@Autowired
    PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository;

	@Test
    public void testCountAllPatientLegalRepresentativeAssociations() {
        Assert.assertNotNull("Data on demand for 'PatientLegalRepresentativeAssociation' failed to initialize correctly", dod.getRandomPatientLegalRepresentativeAssociation());
        long count = patientLegalRepresentativeAssociationService.countAllPatientLegalRepresentativeAssociations();
        Assert.assertTrue("Counter for 'PatientLegalRepresentativeAssociation' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindPatientLegalRepresentativeAssociation() {
        PatientLegalRepresentativeAssociation obj = dod.getRandomPatientLegalRepresentativeAssociation();
        Assert.assertNotNull("Data on demand for 'PatientLegalRepresentativeAssociation' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PatientLegalRepresentativeAssociation' failed to provide an identifier", id);
        obj = patientLegalRepresentativeAssociationService.findPatientLegalRepresentativeAssociation(id);
        Assert.assertNotNull("Find method for 'PatientLegalRepresentativeAssociation' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'PatientLegalRepresentativeAssociation' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllPatientLegalRepresentativeAssociations() {
        Assert.assertNotNull("Data on demand for 'PatientLegalRepresentativeAssociation' failed to initialize correctly", dod.getRandomPatientLegalRepresentativeAssociation());
        long count = patientLegalRepresentativeAssociationService.countAllPatientLegalRepresentativeAssociations();
        Assert.assertTrue("Too expensive to perform a find all test for 'PatientLegalRepresentativeAssociation', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<PatientLegalRepresentativeAssociation> result = patientLegalRepresentativeAssociationService.findAllPatientLegalRepresentativeAssociations();
        Assert.assertNotNull("Find all method for 'PatientLegalRepresentativeAssociation' illegally returned null", result);
        Assert.assertTrue("Find all method for 'PatientLegalRepresentativeAssociation' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindPatientLegalRepresentativeAssociationEntries() {
        Assert.assertNotNull("Data on demand for 'PatientLegalRepresentativeAssociation' failed to initialize correctly", dod.getRandomPatientLegalRepresentativeAssociation());
        long count = patientLegalRepresentativeAssociationService.countAllPatientLegalRepresentativeAssociations();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<PatientLegalRepresentativeAssociation> result = patientLegalRepresentativeAssociationService.findPatientLegalRepresentativeAssociationEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'PatientLegalRepresentativeAssociation' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'PatientLegalRepresentativeAssociation' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        PatientLegalRepresentativeAssociation obj = dod.getRandomPatientLegalRepresentativeAssociation();
        Assert.assertNotNull("Data on demand for 'PatientLegalRepresentativeAssociation' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PatientLegalRepresentativeAssociation' failed to provide an identifier", id);
        obj = patientLegalRepresentativeAssociationService.findPatientLegalRepresentativeAssociation(id);
        Assert.assertNotNull("Find method for 'PatientLegalRepresentativeAssociation' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyPatientLegalRepresentativeAssociation(obj);
        Integer currentVersion = obj.getVersion();
        patientLegalRepresentativeAssociationRepository.flush();
        Assert.assertTrue("Version for 'PatientLegalRepresentativeAssociation' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdatePatientLegalRepresentativeAssociationUpdate() {
        PatientLegalRepresentativeAssociation obj = dod.getRandomPatientLegalRepresentativeAssociation();
        Assert.assertNotNull("Data on demand for 'PatientLegalRepresentativeAssociation' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PatientLegalRepresentativeAssociation' failed to provide an identifier", id);
        obj = patientLegalRepresentativeAssociationService.findPatientLegalRepresentativeAssociation(id);
        boolean modified =  dod.modifyPatientLegalRepresentativeAssociation(obj);
        Integer currentVersion = obj.getVersion();
        PatientLegalRepresentativeAssociation merged = patientLegalRepresentativeAssociationService.updatePatientLegalRepresentativeAssociation(obj);
        patientLegalRepresentativeAssociationRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'PatientLegalRepresentativeAssociation' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSavePatientLegalRepresentativeAssociation() {
        Assert.assertNotNull("Data on demand for 'PatientLegalRepresentativeAssociation' failed to initialize correctly", dod.getRandomPatientLegalRepresentativeAssociation());
        PatientLegalRepresentativeAssociation obj = dod.getNewTransientPatientLegalRepresentativeAssociation(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'PatientLegalRepresentativeAssociation' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'PatientLegalRepresentativeAssociation' identifier to be null", obj.getId());
        patientLegalRepresentativeAssociationService.savePatientLegalRepresentativeAssociation(obj);
        patientLegalRepresentativeAssociationRepository.flush();
        Assert.assertNotNull("Expected 'PatientLegalRepresentativeAssociation' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeletePatientLegalRepresentativeAssociation() {
        PatientLegalRepresentativeAssociation obj = dod.getRandomPatientLegalRepresentativeAssociation();
        Assert.assertNotNull("Data on demand for 'PatientLegalRepresentativeAssociation' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PatientLegalRepresentativeAssociation' failed to provide an identifier", id);
        obj = patientLegalRepresentativeAssociationService.findPatientLegalRepresentativeAssociation(id);
        patientLegalRepresentativeAssociationService.deletePatientLegalRepresentativeAssociation(obj);
        patientLegalRepresentativeAssociationRepository.flush();
        Assert.assertNull("Failed to remove 'PatientLegalRepresentativeAssociation' with identifier '" + id + "'", patientLegalRepresentativeAssociationService.findPatientLegalRepresentativeAssociation(id));
    }
}
