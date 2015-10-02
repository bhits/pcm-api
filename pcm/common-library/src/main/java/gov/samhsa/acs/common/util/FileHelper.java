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
package gov.samhsa.acs.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * The Class FileHelper.
 */
public class FileHelper {

	/**
	 * *****************************************
	 * Output string to file
	 * ******************************************.
	 *
	 * @param stringContent the string content
	 * @param fileName the file name
	 * @throws Exception the exception
	 */
	public static void writeStringToFile(String stringContent, String fileName)
			throws Exception {

		PrintWriter printOut = new PrintWriter("c:\\temp\\" + fileName);
		printOut.println(stringContent);
		printOut.close();
	}

	/**
	 * *****************************************
	 * Output Document to file
	 * ******************************************.
	 *
	 * @param doc the doc
	 * @param fileName the file name
	 * @throws Exception the exception
	 */
	public static void writeDocToFile(Document doc, String fileName)
			throws Exception {
		File encryptionFile = new File("c:\\temp\\" +fileName);
		FileOutputStream f = new FileOutputStream(encryptionFile);

		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(f);
		transformer.transform(source, result);

		f.close();
	}

	/**
	 * *****************************************
	 * Output Bytes to file
	 * ******************************************.
	 *
	 * @param byteContent the byte content
	 * @param fileName the file name
	 * @throws Exception the exception
	 */
	public static void writeBytesToFile(byte[] byteContent, String fileName)
			throws Exception {

		File kekFile = new File("c:\\temp\\" +fileName);
		FileOutputStream f = new FileOutputStream(kekFile);
		f.write(byteContent);
		f.close();
	}	
}
