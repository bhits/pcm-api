package gov.samhsa.acs.common.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class EncryptToolTest {

	@Test
	public void testConstructor(){
		EncryptTool encryptTool=new EncryptTool (); 
	}
	
	@Test
	public void testGenerateKeyEncryptionKey() throws Exception {
		//Subject to change if algorithm changes
		assertEquals(EncryptTool.generateKeyEncryptionKey().getClass().getName(),"com.sun.crypto.provider.DESedeKey");
	}
	
	@Test
	public void testGenerateDataEncryptionKey() throws Exception {
		//Subject to change if algorithm changes
		assertEquals(EncryptTool.generateDataEncryptionKey().getClass().getName(),"javax.crypto.spec.SecretKeySpec");
	}

}
