package gov.samhsa.consent2share.domain.reference;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@ActiveProfiles("test")
@Transactional
@Configurable
public class UnitOfMeasureCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    UnitOfMeasureCodeDataOnDemand dod;

	@Autowired
    UnitOfMeasureCodeRepository unitOfMeasureCodeRepository;

	@Test
    public void testCountAllUnitOfMeasureCodes() {
        Assert.assertNotNull("Data on demand for 'UnitOfMeasureCode' failed to initialize correctly", dod.getRandomUnitOfMeasureCode());
        long count = unitOfMeasureCodeRepository.count();
        Assert.assertTrue("Counter for 'UnitOfMeasureCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindUnitOfMeasureCode() {
        UnitOfMeasureCode obj = dod.getRandomUnitOfMeasureCode();
        Assert.assertNotNull("Data on demand for 'UnitOfMeasureCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'UnitOfMeasureCode' failed to provide an identifier", id);
        obj = unitOfMeasureCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'UnitOfMeasureCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'UnitOfMeasureCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllUnitOfMeasureCodes() {
        Assert.assertNotNull("Data on demand for 'UnitOfMeasureCode' failed to initialize correctly", dod.getRandomUnitOfMeasureCode());
        long count = unitOfMeasureCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'UnitOfMeasureCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<UnitOfMeasureCode> result = unitOfMeasureCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'UnitOfMeasureCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'UnitOfMeasureCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindUnitOfMeasureCodeEntries() {
        Assert.assertNotNull("Data on demand for 'UnitOfMeasureCode' failed to initialize correctly", dod.getRandomUnitOfMeasureCode());
        long count = unitOfMeasureCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<UnitOfMeasureCode> result = unitOfMeasureCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'UnitOfMeasureCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'UnitOfMeasureCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        UnitOfMeasureCode obj = dod.getRandomUnitOfMeasureCode();
        Assert.assertNotNull("Data on demand for 'UnitOfMeasureCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'UnitOfMeasureCode' failed to provide an identifier", id);
        obj = unitOfMeasureCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'UnitOfMeasureCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyUnitOfMeasureCode(obj);
        Integer currentVersion = obj.getVersion();
        unitOfMeasureCodeRepository.flush();
        Assert.assertTrue("Version for 'UnitOfMeasureCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateUnitOfMeasureCodeUpdate() {
        UnitOfMeasureCode obj = dod.getRandomUnitOfMeasureCode();
        Assert.assertNotNull("Data on demand for 'UnitOfMeasureCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'UnitOfMeasureCode' failed to provide an identifier", id);
        obj = unitOfMeasureCodeRepository.findOne(id);
        boolean modified =  dod.modifyUnitOfMeasureCode(obj);
        Integer currentVersion = obj.getVersion();
        UnitOfMeasureCode merged = (UnitOfMeasureCode)unitOfMeasureCodeRepository.save(obj);
        unitOfMeasureCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'UnitOfMeasureCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveUnitOfMeasureCode() {
        Assert.assertNotNull("Data on demand for 'UnitOfMeasureCode' failed to initialize correctly", dod.getRandomUnitOfMeasureCode());
        UnitOfMeasureCode obj = dod.getNewTransientUnitOfMeasureCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'UnitOfMeasureCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'UnitOfMeasureCode' identifier to be null", obj.getId());
        unitOfMeasureCodeRepository.save(obj);
        unitOfMeasureCodeRepository.flush();
        Assert.assertNotNull("Expected 'UnitOfMeasureCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteUnitOfMeasureCode() {
        UnitOfMeasureCode obj = dod.getRandomUnitOfMeasureCode();
        Assert.assertNotNull("Data on demand for 'UnitOfMeasureCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'UnitOfMeasureCode' failed to provide an identifier", id);
        obj = unitOfMeasureCodeRepository.findOne(id);
        unitOfMeasureCodeRepository.delete(obj);
        unitOfMeasureCodeRepository.flush();
        Assert.assertNull("Failed to remove 'UnitOfMeasureCode' with identifier '" + id + "'", unitOfMeasureCodeRepository.findOne(id));
    }
}
