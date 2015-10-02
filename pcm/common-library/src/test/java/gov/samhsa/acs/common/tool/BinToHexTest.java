package gov.samhsa.acs.common.tool;


import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Test;

public class BinToHexTest {
	
	@Test
	public void testToHexFromByte(){
		byte data = 45;
		String hexData=BinToHex.toHexFromByte(data);
		Assert.assertEquals("2d", hexData);
	}
	
	@Test
	public void testToHexFromBytes(){
		byte[] data = {45,67,89};
		String hexData=BinToHex.toHexFromBytes(data);
		Assert.assertEquals("2d4359", hexData);
	}
	
	@Test
	public void testToHexFromBytes_bytesEqualNull(){
		byte[] data=null;
		String hexData=BinToHex.toHexFromBytes(data);
		Assert.assertEquals("", hexData);
	}
	
	@Test
	public void testToHexFromBytes_byteslength(){
		byte[] data = {};
		String hexData=BinToHex.toHexFromBytes(data);
		Assert.assertEquals("", hexData);
	}
	
	@Test
	public void testReadFile() throws IOException{
		File file=new File("src/test/resources/xmlString3.txt");
		byte[] bytes=BinToHex.readFile(file);
		Assert.assertNotNull(bytes);
	}
	
}
