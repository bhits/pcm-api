package gov.samhsa.acs.brms.guvnor;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class GuvnorServiceImplIT {
	private static String GUNVOR_REST_URL;
	private static String USERNAME;
	private static String PASSWORD;

	private GuvnorServiceImpl sut;
	
	@Before
	public void setUp() throws URISyntaxException, IOException{
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL resource = classloader.getResource("guvnor.properties");
		File file = new File(resource.toURI());
		FileInputStream fis = new FileInputStream(file);
		Properties props = new Properties();
		props.load(fis);
		GUNVOR_REST_URL = props.getProperty("guvnor.service");
		USERNAME = props.getProperty("guvnor.username");
		PASSWORD = props.getProperty("guvnor.password");
	}

	@Test
	public void testGetVersionedRulesFromPackage() throws IOException {
		// Arrange
		sut = new GuvnorServiceImpl(GUNVOR_REST_URL, USERNAME, PASSWORD);

		// Act
		String response = sut.getVersionedRulesFromPackage();

		// Assert
		assertNotNull(response);
		assertNotEquals("", response);
	}
}
