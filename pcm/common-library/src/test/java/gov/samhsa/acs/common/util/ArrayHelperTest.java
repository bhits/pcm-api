package gov.samhsa.acs.common.util;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.*;

import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.util.ArrayHelper;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class ArrayHelperTest {
	
	@Test
	public void testHasSubArray_SubArrayAtTheEnd(){
		byte[] array = new byte[3];
		array[0] = 1;
		array[1] = 2;
		array[2] = 3;
		
		byte[] subArray = new byte[1];
		subArray[0] = 3;
		
		boolean hasSubArray = ArrayHelper.hasSubArray(array, subArray);
		
		Assert.assertTrue(hasSubArray);
	}
	
	@Test
	public void testHasSubArray_SubArrayAtTheBegining(){
		byte[] array = new byte[3];
		array[0] = 1;
		array[1] = 2;
		array[2] = 3;
		
		byte[] subArray = new byte[1];
		subArray[0] = 1;
		
		boolean hasSubArray = ArrayHelper.hasSubArray(array, subArray);
		
		Assert.assertTrue(hasSubArray);
	}
	
	@Test
	public void testHasSubArray_SubArrayInTheMiddle(){
		byte[] array = new byte[3];
		array[0] = 1;
		array[1] = 2;
		array[2] = 3;
		
		byte[] subArray = new byte[1];
		subArray[0] = 2;
		
		boolean hasSubArray = ArrayHelper.hasSubArray(array, subArray);
		
		Assert.assertTrue(hasSubArray);
	}
	
	@Test
	public void testHasSubArray_NotFound(){
		byte[] array = new byte[3];
		array[0] = 1;
		array[1] = 2;
		array[2] = 3;
		
		byte[] subArray = new byte[1];
		subArray[0] = 4;
		
		boolean hasSubArray = ArrayHelper.hasSubArray(array, subArray);
		
		Assert.assertTrue(!hasSubArray);
	}
	
	@Test
	public void testResourceToChars(){
				
		char[] hasSubArray = ArrayHelper.resourceToChars("validC32.xml");
		
		Assert.assertNotNull(hasSubArray);
	}
	
	@Test
	public void testResourceToBytes(){
				
		byte[] hasSubArray = ArrayHelper.resourceToBytes("validC32.xml");
		
		Assert.assertNotNull(hasSubArray);
	}
	
}
