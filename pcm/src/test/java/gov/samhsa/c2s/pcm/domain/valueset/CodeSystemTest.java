package gov.samhsa.c2s.pcm.domain.valueset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeSystemTest {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CodeSystemTest.class);

    private static final String CODESYSTEM_OID = "codeSystemOId";
    private static final String CODESYSTEM_CODE = "code";
    private static final String CODESYSTEM_NAME = "name";
    private static final String CODESYSTEM_USERNAME = "userName";
    private static final String CODESYSTEM_DISPLAYNAME = "displayName";
    private static final String CODESYSTEM_UPDATE_OID = "updateCodeSystemOId";
    private static final String CODESYSTEM_UPDATE_CODE = "updateCode";
    private static final String CODESYSTEM_UPDATE_NAME = "updateName";
    private static final String CODESYSTEM_UPDATE_USERNAME = "updateUserName";
    private static final String CODESYSTEM_UPDATE_DISPLAYNAME = "updateDisplayName";

    private CodeSystem codeSystem;
    @Before
    public void setUpCodeSystem(){
        codeSystem = CodeSystem.getBuilder(CODESYSTEM_OID,
                CODESYSTEM_CODE, CODESYSTEM_NAME, CODESYSTEM_USERNAME).build();
    }


    private void assertCodeSystem(CodeSystem codeSystem) {
        assertEquals(CODESYSTEM_OID, codeSystem.getCodeSystemOId());
        //	assertEquals(CODESYSTEM_CODE, codeSystem.getCode());
        assertEquals(CODESYSTEM_NAME, codeSystem.getName());
        assertEquals(CODESYSTEM_USERNAME, codeSystem.getUserName());
        assertNull(codeSystem.getCreationTime());
        assertNull(codeSystem.getModificationTime());
        assertNull(codeSystem.getId());
        LOGGER.info("codesystem in string" + codeSystem);
    }

    private void assertUpdateCodeSystem(CodeSystem codeSystem) {
        assertEquals(CODESYSTEM_UPDATE_OID, codeSystem.getCodeSystemOId());
//		assertEquals(CODESYSTEM_UPDATE_CODE, codeSystem.getCode());
        assertEquals(CODESYSTEM_UPDATE_NAME, codeSystem.getName());
        assertEquals(CODESYSTEM_UPDATE_USERNAME, codeSystem.getUserName());
        assertNull(codeSystem.getCreationTime());
        assertNull(codeSystem.getModificationTime());
        assertNull(codeSystem.getId());
    }

    @Test
    public void build() {
        assertCodeSystem(codeSystem);
    }

    @Test
    public void testCodeSystem() {
        codeSystem = new CodeSystem();
        codeSystem.setCodeSystemOId(CODESYSTEM_OID);
//		codeSystem.setCode(CODESYSTEM_CODE);
        codeSystem.setName(CODESYSTEM_NAME);
        codeSystem.setDisplayName(CODESYSTEM_DISPLAYNAME);
        codeSystem.setUserName(CODESYSTEM_USERNAME);

        assertCodeSystem(codeSystem);
    }

/*	@Test
	public void testGetBuilder() {
		fail("Not yet implemented");
	}*/

    @Test
    public void testUpdate() {
        codeSystem.update(CODESYSTEM_UPDATE_OID,
                CODESYSTEM_UPDATE_CODE, CODESYSTEM_UPDATE_NAME,CODESYSTEM_UPDATE_DISPLAYNAME, CODESYSTEM_UPDATE_USERNAME);
        assertUpdateCodeSystem(codeSystem);
        assertEquals(CODESYSTEM_UPDATE_DISPLAYNAME, codeSystem.getDisplayName());

    }

/*	@Test
	public void testGetCurrentDate() {
		fail("Not yet implemented");
	}*/

    @Test
    public void testPrePersist() {
        CodeSystem codeSystem = CodeSystem.getBuilder(CODESYSTEM_OID,
                CODESYSTEM_CODE, CODESYSTEM_NAME, CODESYSTEM_USERNAME).build();
        codeSystem.prePersist();

        Date creationTime = codeSystem.getCreationTime();
        Date modificationTime = codeSystem.getModificationTime();

        assertNotNull(creationTime);
        assertNotNull(modificationTime);
        assertEquals(creationTime, modificationTime);
    }

    @Test
    public void testPreUpdate() {
        CodeSystem codeSystem = CodeSystem.getBuilder(CODESYSTEM_OID,
                CODESYSTEM_CODE, CODESYSTEM_NAME, CODESYSTEM_USERNAME).build();
        codeSystem.prePersist();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Back to work
        }

        codeSystem.preUpdate();

        Date creationTime = codeSystem.getCreationTime();
        Date modificationTime = codeSystem.getModificationTime();

        assertNotNull(creationTime);
        assertNotNull(modificationTime);
        assertTrue(modificationTime.after(creationTime));
    }

}
