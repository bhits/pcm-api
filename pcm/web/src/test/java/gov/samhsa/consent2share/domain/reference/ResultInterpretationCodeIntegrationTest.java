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

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@ActiveProfiles("test")
@Transactional
public class ResultInterpretationCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ResultInterpretationCodeDataOnDemand dod;
	
	@Autowired
    ResultInterpretationCodeRepository resultInterpretationCodeRepository;

	@Test
    public void testCountAllResultInterpretationCodes() {
        Assert.assertNotNull("Data on demand for 'ResultInterpretationCode' failed to initialize correctly", dod.getRandomResultInterpretationCode());
        long count = resultInterpretationCodeRepository.count();
        Assert.assertTrue("Counter for 'ResultInterpretationCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindResultInterpretationCode() {
        ResultInterpretationCode obj = dod.getRandomResultInterpretationCode();
        Assert.assertNotNull("Data on demand for 'ResultInterpretationCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ResultInterpretationCode' failed to provide an identifier", id);
        obj = resultInterpretationCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ResultInterpretationCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ResultInterpretationCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllResultInterpretationCodes() {
        Assert.assertNotNull("Data on demand for 'ResultInterpretationCode' failed to initialize correctly", dod.getRandomResultInterpretationCode());
        long count = resultInterpretationCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ResultInterpretationCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ResultInterpretationCode> result = resultInterpretationCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'ResultInterpretationCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ResultInterpretationCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindResultInterpretationCodeEntries() {
        Assert.assertNotNull("Data on demand for 'ResultInterpretationCode' failed to initialize correctly", dod.getRandomResultInterpretationCode());
        long count = resultInterpretationCodeRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<ResultInterpretationCode> result = resultInterpretationCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'ResultInterpretationCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ResultInterpretationCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        ResultInterpretationCode obj = dod.getRandomResultInterpretationCode();
        Assert.assertNotNull("Data on demand for 'ResultInterpretationCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ResultInterpretationCode' failed to provide an identifier", id);
        obj = resultInterpretationCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'ResultInterpretationCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyResultInterpretationCode(obj);
        Integer currentVersion = obj.getVersion();
        resultInterpretationCodeRepository.flush();
        Assert.assertTrue("Version for 'ResultInterpretationCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateResultInterpretationCodeUpdate() {
        ResultInterpretationCode obj = dod.getRandomResultInterpretationCode();
        Assert.assertNotNull("Data on demand for 'ResultInterpretationCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ResultInterpretationCode' failed to provide an identifier", id);
        obj = resultInterpretationCodeRepository.findOne(id);
        boolean modified =  dod.modifyResultInterpretationCode(obj);
        Integer currentVersion = obj.getVersion();
        ResultInterpretationCode merged = (ResultInterpretationCode)resultInterpretationCodeRepository.save(obj);
        resultInterpretationCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ResultInterpretationCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveResultInterpretationCode() {
        Assert.assertNotNull("Data on demand for 'ResultInterpretationCode' failed to initialize correctly", dod.getRandomResultInterpretationCode());
        ResultInterpretationCode obj = dod.getNewTransientResultInterpretationCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ResultInterpretationCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ResultInterpretationCode' identifier to be null", obj.getId());
        resultInterpretationCodeRepository.save(obj);
        resultInterpretationCodeRepository.flush();
        Assert.assertNotNull("Expected 'ResultInterpretationCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteResultInterpretationCode() {
        ResultInterpretationCode obj = dod.getRandomResultInterpretationCode();
        Assert.assertNotNull("Data on demand for 'ResultInterpretationCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ResultInterpretationCode' failed to provide an identifier", id);
        obj = resultInterpretationCodeRepository.findOne(id);
        resultInterpretationCodeRepository.delete(obj);
        resultInterpretationCodeRepository.flush();
        Assert.assertNull("Failed to remove 'ResultInterpretationCode' with identifier '" + id + "'", resultInterpretationCodeRepository.findOne(id));
    }
}
