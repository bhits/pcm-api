package gov.samhsa.c2s.pcm;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Ignore("Depends on config-server on bootstrap")
public class PatientConsentManagementApplicationIntegrationTest {

    @Ignore("Integration Test: requires mysql connection with default configuration")
    @Test
    public void contextLoads() {
    }
}