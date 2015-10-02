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
public class RouteCodeIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    RouteCodeDataOnDemand dod;

	@Autowired
    RouteCodeRepository routeCodeRepository;

	@Test
    public void testCountAllRouteCodes() {
        Assert.assertNotNull("Data on demand for 'RouteCode' failed to initialize correctly", dod.getRandomRouteCode());
        long count = routeCodeRepository.count();
        Assert.assertTrue("Counter for 'RouteCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindRouteCode() {
        RouteCode obj = dod.getRandomRouteCode();
        Assert.assertNotNull("Data on demand for 'RouteCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'RouteCode' failed to provide an identifier", id);
        obj = routeCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'RouteCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'RouteCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllRouteCodes() {
        Assert.assertNotNull("Data on demand for 'RouteCode' failed to initialize correctly", dod.getRandomRouteCode());
        long count = routeCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'RouteCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<RouteCode> result = routeCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'RouteCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'RouteCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindRouteCodeEntries() {
        Assert.assertNotNull("Data on demand for 'RouteCode' failed to initialize correctly", dod.getRandomRouteCode());
        long count = routeCodeRepository.count();
        if (count > 20) count = 20;
        int pageNumber = 0;
        int pageSize = (int) count;
        List<RouteCode> result = routeCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'RouteCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'RouteCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        RouteCode obj = dod.getRandomRouteCode();
        Assert.assertNotNull("Data on demand for 'RouteCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'RouteCode' failed to provide an identifier", id);
        obj = routeCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'RouteCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyRouteCode(obj);
        Integer currentVersion = obj.getVersion();
        routeCodeRepository.flush();
        Assert.assertTrue("Version for 'RouteCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateRouteCodeUpdate() {
        RouteCode obj = dod.getRandomRouteCode();
        Assert.assertNotNull("Data on demand for 'RouteCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'RouteCode' failed to provide an identifier", id);
        obj = routeCodeRepository.findOne(id);
        boolean modified =  dod.modifyRouteCode(obj);
        Integer currentVersion = obj.getVersion();
        RouteCode merged = (RouteCode)routeCodeRepository.save(obj);
        routeCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'RouteCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveRouteCode() {
        Assert.assertNotNull("Data on demand for 'RouteCode' failed to initialize correctly", dod.getRandomRouteCode());
        RouteCode obj = dod.getNewTransientRouteCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'RouteCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'RouteCode' identifier to be null", obj.getId());
        routeCodeRepository.save(obj);
        routeCodeRepository.flush();
        Assert.assertNotNull("Expected 'RouteCode' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteRouteCode() {
        RouteCode obj = dod.getRandomRouteCode();
        Assert.assertNotNull("Data on demand for 'RouteCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'RouteCode' failed to provide an identifier", id);
        obj = routeCodeRepository.findOne(id);
        routeCodeRepository.delete(obj);
        routeCodeRepository.flush();
        Assert.assertNull("Failed to remove 'RouteCode' with identifier '" + id + "'", routeCodeRepository.findOne(id));
    }
}
