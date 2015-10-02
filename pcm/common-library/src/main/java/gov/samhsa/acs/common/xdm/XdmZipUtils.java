/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.acs.common.xdm;

import gov.samhsa.acs.common.exception.DS4PException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * The Class XdmZipUtils.
 */
public class XdmZipUtils {

	/** The Constant XDM_SUB_FOLDER. */
	private static final String XDM_SUB_FOLDER = "SUBSET01/";
	
	/** The Constant PATH_INDEX. */
	public static final String PATH_INDEX = "INDEX.html";
	
	/** The Constant PATH_README. */
	public static final String PATH_README = "README.txt";
	
	/** The Constant PATH_METADATA. */
	public static final String PATH_METADATA = XDM_SUB_FOLDER + "METADATA.xml";
	
	/** The Constant PATH_C32. */
	public static final String PATH_C32 = XDM_SUB_FOLDER + "DOCUMENT.xml";
	
	/** The Constant PATH_KEKENCRYPT. */
	public static final String PATH_KEKENCRYPT = "kekEncryptionKey";
	
	/** The Constant PATH_KEKMASKING. */
	public static final String PATH_KEKMASKING = "kekMaskingKey";
	
	/** The Constant PATH_XSL. */
	public static final String PATH_XSL = XDM_SUB_FOLDER + "CDA.xsl";
	
	/**
	 * Creates the xdm package.
	 *
	 * @param metadata the metadata
	 * @param xsl the xsl
	 * @param c32 the c32
	 * @param index the index
	 * @param readMe the read me
	 * @param kekMaskingKey the kek masking key (nillable)
	 * @param kekEncryptionKey the kek encryption key (nillable)
	 * @return the byte[]
	 */
	public static byte[] createXDMPackage(String metadata, String xsl, String c32,
			String index, String readMe, byte[] kekMaskingKey,
			byte[] kekEncryptionKey) {

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(os);
		

		byte[] bytesOut = null;

		byte[] c32Bytes = getUtfCustomBytes(c32);
		byte[] metaBytes = getUtfCustomBytes(metadata);
		byte[] xslBytes = getUtfCustomBytes(xsl);
		byte[] indexBytes = getUtfCustomBytes(index);
		byte[] readMeBytes = getUtfCustomBytes(readMe);

		try {
			addZipEntry(PATH_C32, c32Bytes, zos);
			addZipEntry(PATH_README, readMeBytes, zos);
			addZipEntry(PATH_INDEX, indexBytes, zos);
			addZipEntry(PATH_XSL, xslBytes, zos);
			addZipEntry(PATH_METADATA, metaBytes, zos);
			if(kekEncryptionKey != null){
				addZipEntry(PATH_KEKENCRYPT, kekEncryptionKey, zos);	
			}
			if(kekMaskingKey != null){
				addZipEntry(PATH_KEKMASKING, kekMaskingKey, zos);				
			}			
			
			zos.finish();
			bytesOut = os.toByteArray();

		}
		catch (IOException e) {
			throw new DS4PException(e.toString(), e);
		}
		finally {
			try {
				zos.flush();
				zos.close();
				os.flush();
				os.close();
			}
			catch (IOException e) {
				// do nothing here
			}
		}
		return bytesOut;
	}

	/**
	 * Gets the utf custom bytes.
	 *
	 * @param str the str
	 * @return the utf custom bytes
	 */
	public static byte[] getUtfCustomBytes(String str) {
		byte[] b = new byte[str.length()];
		for (int i = 0; i < str.length(); i++) {

			char strChar = str.charAt(i);
			int bpos = i;

			b[bpos] =  (byte) (strChar & 0x00FF);
		}
		return b;

	}
	
	/**
	 * Adds the zip entry.
	 *
	 * @param pathName the path name
	 * @param contents the contents
	 * @param zos the zos
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void addZipEntry(String pathName, byte[] contents, ZipOutputStream zos)
			throws IOException {
		ZipEntry entry = new ZipEntry(pathName);
		entry.setSize(contents.length);
		zos.putNextEntry(entry);
		zos.write(contents);
		zos.closeEntry();
	}
	
	/**
	 * Gets the entry bytes.
	 *
	 * @param zipFileBytes the zip file bytes
	 * @param entryName the entry name
	 * @param entrySize the entry size
	 * @return the entry bytes
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] getEntryBytes(byte[] zipFileBytes,
			String entryName, int entrySize) throws IOException {
		ByteArrayInputStream file = new ByteArrayInputStream(zipFileBytes);
		ZipInputStream zip_inputstream = new ZipInputStream(file);
		ZipEntry current_zip_entry = null;
		byte[] buf = new byte[entrySize];
		boolean found = false;
		current_zip_entry = zip_inputstream.getNextEntry();
		while ((current_zip_entry != null) && !found) {
			if (current_zip_entry.getName().equals(entryName)) {
				found = true;
				ByteArrayOutputStream output = getByteArrayOutputStream(zip_inputstream);
				buf = output.toByteArray();
				output.flush();
				output.close();
			} else {
				current_zip_entry = zip_inputstream.getNextEntry();
			}
		}

		zip_inputstream.close();
		file.close();
		return buf;
	}

	/**
	 * Gets the byte array output stream.
	 *
	 * @param zipInputstream the zip inputstream
	 * @return the byte array output stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static ByteArrayOutputStream getByteArrayOutputStream(
			ZipInputStream zipInputstream) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int data = 0;
		while ((data = zipInputstream.read()) != -1) {
			output.write(data);
		}
		return output;
	}

	/**
	 * Write zipfile.
	 *
	 * @param zipPath the zip path
	 * @param bytes the bytes
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeZipfile(String zipPath, byte[] bytes) throws IOException {

		FileOutputStream zipos = new FileOutputStream(zipPath);
		ZipOutputStream zout = new ZipOutputStream(zipos);

		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ZipInputStream zipis = new ZipInputStream(bis);

		ZipEntry current_zip_entry = null;
		boolean found = false;
		current_zip_entry = zipis.getNextEntry();

		while ((current_zip_entry != null) && !found) {
			zout.putNextEntry(current_zip_entry);
			byte[] buf = new byte[4096];
			int bytesRead = 0;
			while ((bytesRead = zipis.read(buf)) != -1) {
				zout.write(buf, 0, bytesRead);
			}
			current_zip_entry = zipis.getNextEntry();
		}
		bis.close();
		zout.close();
	}
}
