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

import gov.samhsa.acs.common.exception.DS4PException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;

/**
 * The Class ArrayHelper.
 */
public class ArrayHelper {
	
	/**
	 * Checks for sub array.
	 *
	 * @param array the array
	 * @param arrayToFind the array to find
	 * @return true, if successful
	 */
	public static boolean hasSubArray(byte[] array, byte[] arrayToFind) {
		int findLen = arrayToFind.length;
		int maxIdx = array.length - findLen ;
		boolean found = false;
		for (int i = 0; (i <= maxIdx) && !found; i++) {
			byte[] subArray = ArrayUtils.subarray(array, i, i+findLen);
			found = Arrays.equals(arrayToFind, subArray);
		}
		return found;
	}
	
	/**
	 * Resource to chars.
	 *
	 * @param resourcePath the resource path
	 * @return the char[]
	 */
	public static char[] resourceToChars(String resourcePath){
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream is = loader.getResourceAsStream(resourcePath);
		char[] chars = new char[0];
		try {
			chars = IOUtils.toCharArray(is);
		} catch (IOException e) {
			throw new DS4PException(e.toString(), e);
		}
		return chars;
	}
	
	/**
	 * Resource to bytes.
	 *
	 * @param resourcePath the resource path
	 * @return the byte[]
	 */
	public static byte[] resourceToBytes(String resourcePath){
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream is = loader.getResourceAsStream(resourcePath);
		byte[] bytes = new byte[0];
		try {
			bytes = IOUtils.toByteArray(is);
		} catch (IOException e) {
			throw new DS4PException(e.toString(), e);
		}
		return bytes;
	}
}
