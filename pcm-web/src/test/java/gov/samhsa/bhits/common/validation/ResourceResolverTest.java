package gov.samhsa.bhits.common.validation;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.ls.LSInput;

@RunWith(MockitoJUnitRunner.class)
public class ResourceResolverTest {

    private static final String BASE_PATH = "schema/a/b/main/";

    private Map<String,String> pathMap;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private InputStream resourceMock;

    @InjectMocks
    @Spy
    private ResourceResolver sut;

    @Before
    public void setUp() throws Exception {
        // common arrange
        pathMap = new HashMap<String,String>();
        ReflectionTestUtils.setField(sut, "schemaBasePath", BASE_PATH);
        ReflectionTestUtils.setField(sut, "pathMap", pathMap);
        when(sut.getResourceAsStream(anyString())).thenReturn(resourceMock);
        doReturn(new Scanner("s")).when(sut).setScanner(resourceMock);
    }

    @Test
    public void testResolveResource_In_Base_Path() {
        // Act
        LSInput input = sut.resolveResource("type", "namespaceURI", "publicId", "new.xsd", "a/b/base.xsd");

        // Assert
        assertEquals(BASE_PATH,pathMap.get("new.xsd"));
        assertNotNull(input);
    }

    @Test
    public void testResolveResource_In_BaseURI_Path() {
        // Arrange
        pathMap.put("base.xsd", BASE_PATH+"c/d/");

        // Act
        LSInput input = sut.resolveResource("type", "namespaceURI", "publicId", "new.xsd", "a/b/base.xsd");

        // Assert
        assertEquals(BASE_PATH+"c/d/",pathMap.get("new.xsd"));
        assertNotNull(input);
    }

    @Test
    public void testResolveResource_In_BaseURI_One_Upper_Level() {
        // Arrange
        pathMap.put("base.xsd", BASE_PATH+"c/d/");

        // Act
        LSInput input = sut.resolveResource("type", "namespaceURI", "publicId", "../new.xsd", "a/b/base.xsd");

        // Assert
        assertEquals(BASE_PATH+"c/",pathMap.get("new.xsd"));
        assertNotNull(input);
    }

    @Test
    public void testResolveResource_In_BaseURI_Two_Upper_Levels() {
        // Arrange
        pathMap.put("base.xsd", BASE_PATH+"c/d/");

        // Act
        LSInput input = sut.resolveResource("type", "namespaceURI", "publicId", "../../x/new.xsd", "a/b/base.xsd");

        // Assert
        assertEquals(BASE_PATH+"x/",pathMap.get("new.xsd"));
        assertNotNull(input);
    }
}
