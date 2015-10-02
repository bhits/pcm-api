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
package gov.samhsa.consent2share.pixclient.service;

import org.hl7.v3.types.MCCIIN000002UV01;
import org.hl7.v3.types.PRPAIN201301UV02;
import org.hl7.v3.types.PRPAIN201302UV02;
import org.hl7.v3.types.PRPAIN201304UV02;
import org.hl7.v3.types.PRPAIN201309UV02;
import org.hl7.v3.types.PRPAIN201310UV02;

/**
 * The Interface PixManagerService.
 */
public interface PixManagerService {

	/**
	 * Pix manager PRPAIN201301UV02 (Add).
	 * 
	 * @param body
	 *            the body
	 * @return the MCCIIN000002UV01 (Acknowledgement)
	 */
	public abstract MCCIIN000002UV01 pixManagerPRPAIN201301UV02(
			PRPAIN201301UV02 body);

	/**
	 * Pix manager PRPAIN201302UV02 (Update).
	 * 
	 * @param body
	 *            the body
	 * @return the MCCIIN000002UV01 (Acknowledgement)
	 */
	public abstract MCCIIN000002UV01 pixManagerPRPAIN201302UV02(
			PRPAIN201302UV02 body);

	/**
	 * Pix manager PRPAIN201304UV02 (Merge).
	 * 
	 * @param body
	 *            the body
	 * @return the MCCIIN000002UV01 (Acknowledgement)
	 */
	public abstract MCCIIN000002UV01 pixManagerPRPAIN201304UV02(
			PRPAIN201304UV02 body);

	/**
	 * Pix manager PRPAIN201309UV02 (Query).
	 * 
	 * @param body
	 *            the body
	 * @return the PRPAIN201310UV02 (Query Response)
	 */
	public abstract PRPAIN201310UV02 pixManagerPRPAIN201309UV02(
			PRPAIN201309UV02 body);
}