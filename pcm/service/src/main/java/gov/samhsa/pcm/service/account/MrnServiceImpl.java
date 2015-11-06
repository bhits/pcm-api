/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *   
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *   
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.pcm.service.account;

import gov.samhsa.pcm.common.UniqueValueGenerator;
import gov.samhsa.pcm.domain.patient.PatientRepository;

import org.apache.commons.lang.RandomStringUtils;

/**
 * The Class MrnServiceImpl.
 */
public class MrnServiceImpl implements MrnService {

	/** The Constant RANDOM_STRING_LENGTH. */
	private static final int RANDOM_STRING_LENGTH = 6;

	/** The prefix. */
	private String prefix;

	/** The patient repository. */
	private PatientRepository patientRepository;

	/**
	 * Instantiates a new mrn service impl.
	 *
	 * @param prefix
	 *            the prefix
	 * @param patientRepository
	 *            the patient repository
	 */
	public MrnServiceImpl(String prefix, PatientRepository patientRepository) {
		super();
		this.prefix = prefix;
		this.patientRepository = patientRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.account.MrnService#generateMrn()
	 */
	@Override
	public String generateMrn() {
		final short iterationLimit = 3;
		return UniqueValueGenerator
				.generateUniqueValue(this::generateRandomMrn,
						generatedValue -> patientRepository
								.findAllByMedicalRecordNumber(generatedValue)
								.size() == 0, iterationLimit);
	}

	/**
	 * Generate random mrn.
	 *
	 * @return the string
	 */
	private String generateRandomMrn() {
		StringBuilder localIdIdBuilder = new StringBuilder();
		if (null != prefix) {
			localIdIdBuilder.append(new String(prefix));
			localIdIdBuilder.append(".");
		}
		localIdIdBuilder.append(RandomStringUtils
				.randomAlphanumeric((RANDOM_STRING_LENGTH)));
		return localIdIdBuilder.toString().toUpperCase();
	}
}
