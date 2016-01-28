package gov.samhsa.mhc.pcm;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PatientConsentManagementApplication.class)
@WebAppConfiguration
public class PatientConsentManagementApplicationIntegrationTest {

    @Ignore("Integration Test: requires mysql connection with default configuration")
    @Test
    public void contextLoads() {
    }

}
