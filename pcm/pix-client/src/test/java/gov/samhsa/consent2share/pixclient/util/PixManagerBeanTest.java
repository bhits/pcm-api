package gov.samhsa.consent2share.pixclient.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PixManagerBeanTest {

	@InjectMocks
	PixManagerBean cstl;

	@Test
	public void testGetAddMessage() {
		// Arrange
		String expectedAddMsg = " Add Success";
		// Act
		cstl.setAddMessage(expectedAddMsg);
		// Assert
		assertEquals(expectedAddMsg, cstl.getAddMessage());
	}

	@Test
	public void testSetAddMessage() {
		// Arrange
		String expectedAddMsg = " Add Success";
		// Act
		cstl.setAddMessage(expectedAddMsg);
		// Assert
		assertEquals(expectedAddMsg, cstl.getAddMessage());
	}

	@Test
	public void testGetUpdateMessage() {
		// Arrange
		String expectedUpdateMsg = " Update Success";
		// Act
		cstl.setUpdateMessage(expectedUpdateMsg);
		// Assert
		assertEquals(expectedUpdateMsg, cstl.getUpdateMessage());
	}

	@Test
	public void testSetUpdateMessage() {
		// Arrange
		String expectedUpdateMsg = " Update Success";
		// Act
		cstl.setUpdateMessage(expectedUpdateMsg);
		// Assert
		assertEquals(expectedUpdateMsg, cstl.getUpdateMessage());
	}

	@Test
	public void testGetQueryMessage() {
		// Arrange
		String expectedQueryMsg = " Query Success";
		// Act
		cstl.setQueryMessage(expectedQueryMsg);
		// Assert
		assertEquals(expectedQueryMsg, cstl.getQueryMessage());
	}

	@Test
	public void testSetQueryMessage() {
		// Arrange
		String expectedQueryMsg = " Query Success";
		// Act
		cstl.setQueryMessage(expectedQueryMsg);
		// Assert
		assertEquals(expectedQueryMsg, cstl.getQueryMessage());
	}

	@Test
	public void testGetQueryIdMap() {
		// Arrange
		 Map<String, String> queryIdMap = new HashMap<String, String>();
		 queryIdMap.put("check","test");
		 
		// Act
		cstl.setQueryIdMap(queryIdMap);
		// Assert
		assertEquals(queryIdMap.get("check"), cstl.getQueryIdMap().get("check"));
	}

	@Test
	public void testSetQueryIdMap() {
		// Arrange
		 Map<String, String> queryIdMap = new HashMap<String, String>();
		 queryIdMap.put("check","test");
		 
		// Act
		cstl.setQueryIdMap(queryIdMap);
		// Assert
		assertEquals(queryIdMap.get("check"), cstl.getQueryIdMap().get("check"));
	}

}
