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
public class RaceCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    RaceCodeDataOnDemand dod;

	@Autowired
    RaceCodeRepository raceCodeRepository;

	@Test
    public void testCountAllRaceCodes() {
        Assert.assertNotNull("Data on demand for 'RaceCode' failed to initialize correctly", dod.getRandomRaceCode());
        long count = raceCodeRepository.count();
        Assert.assertTrue("Counter for 'RaceCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindRaceCode() {
        RaceCode obj = dod.getRandomRaceCode();
        Assert.assertNotNull("Data on demand for 'RaceCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'RaceCode' failed to provide an identifier", id);
        obj = raceCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'RaceCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'RaceCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllRaceCodes() {
        Assert.assertNotNull("Data on demand for 'RaceCode' failed to initialize correctly", dod.getRandomRaceCode());
        long count = raceCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'RaceCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<RaceCode> result = raceCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'RaceCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'RaceCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindRaceCodeEntries() {
        Assert.assertNotNull("Data on demand for 'RaceCode' failed to initialize correctly", dod.getRandomRaceCode());
        long count = raceCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<RaceCode> result = raceCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'RaceCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'RaceCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        RaceCode obj = dod.getRandomRaceCode();
        Assert.assertNotNull("Data on demand for 'RaceCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'RaceCode' failed to provide an identifier", id);
        obj = raceCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'RaceCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyRaceCode(obj);
        Integer currentVersion = obj.getVersion();
        raceCodeRepository.flush();
        Assert.assertTrue("Version for 'RaceCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateRaceCodeUpdate() {
        RaceCode obj = dod.getRandomRaceCode();
        Assert.assertNotNull("Data on demand for 'RaceCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'RaceCode' failed to provide an identifier", id);
        obj = raceCodeRepository.findOne(id);
        boolean modified =  dod.modifyRaceCode(obj);
        Integer currentVersion = obj.getVersion();
        RaceCode merged = (RaceCode)raceCodeRepository.save(obj);
        raceCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'RaceCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveRaceCode() {
        Assert.assertNotNull("Data on demand for 'RaceCode' failed to initialize correctly", dod.getRandomRaceCode());
        RaceCode obj = dod.getNewTransientRaceCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'RaceCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'RaceCode' identifier to be null", obj.getId());
        raceCodeRepository.save(obj);
        raceCodeRepository.flush();
        Assert.assertNotNull("Expected 'RaceCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteRaceCode() {
        RaceCode obj = dod.getRandomRaceCode();
        Assert.assertNotNull("Data on demand for 'RaceCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'RaceCode' failed to provide an identifier", id);
        obj = raceCodeRepository.findOne(id);
        raceCodeRepository.delete(obj);
        raceCodeRepository.flush();
        Assert.assertNull("Failed to remove 'RaceCode' with identifier '" + id + "'", raceCodeRepository.findOne(id));
    }
}
