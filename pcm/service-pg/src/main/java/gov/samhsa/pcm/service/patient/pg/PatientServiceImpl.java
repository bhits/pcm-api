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
package gov.samhsa.pcm.service.patient.pg;

import gov.samhsa.pcm.common.AuthenticatedUser;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.domain.account.Users;
import gov.samhsa.pcm.domain.account.UsersRepository;
import gov.samhsa.pcm.domain.commondomainservices.EmailSender;
import gov.samhsa.pcm.domain.commondomainservices.EmailType;
import gov.samhsa.pcm.domain.patient.Patient;
import gov.samhsa.pcm.domain.patient.PatientLegalRepresentativeAssociationRepository;
import gov.samhsa.pcm.domain.patient.PatientRepository;
import gov.samhsa.pcm.infrastructure.DtoToDomainEntityMapper;
import gov.samhsa.pcm.infrastructure.PixService;
import gov.samhsa.pcm.infrastructure.security.AuthenticationFailedException;
import gov.samhsa.pcm.service.dto.PatientProfileDto;
import gov.samhsa.pcm.service.spirit.SpiritQueryService;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class PatientServiceImpl.
 */
@Transactional
public class PatientServiceImpl extends
		gov.samhsa.pcm.service.patient.PatientServiceImpl {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The spirit query service. */
	private SpiritQueryService spiritQueryService;

	/**
	 * Instantiates a new patient service impl.
	 *
	 * @param patientRepository
	 *            the patient repository
	 * @param patientLegalRepresentativeAssociationRepository
	 *            the patient legal representative association repository
	 * @param modelMapper
	 *            the model mapper
	 * @param userContext
	 *            the user context
	 * @param patientProfileDtoToPatientMapper
	 *            the patient profile dto to patient mapper
	 * @param usersRepository
	 *            the users repository
	 * @param passwordEncoder
	 *            the password encoder
	 * @param emailSender
	 *            the email sender
	 * @param pixService
	 *            the pix service
	 * @param spiritQueryService
	 *            the spirit query service
	 */
	public PatientServiceImpl(
			PatientRepository patientRepository,
			PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository,
			ModelMapper modelMapper,
			UserContext userContext,
			DtoToDomainEntityMapper<PatientProfileDto, Patient> patientProfileDtoToPatientMapper,
			UsersRepository usersRepository, PasswordEncoder passwordEncoder,
			EmailSender emailSender, PixService pixService,
			SpiritQueryService spiritQueryService) {
		super(patientRepository,
				patientLegalRepresentativeAssociationRepository, modelMapper,
				userContext, patientProfileDtoToPatientMapper, usersRepository,
				passwordEncoder, emailSender, pixService);
		this.spiritQueryService = spiritQueryService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.patient.PatientServiceImpl#updatePatient
	 * (gov.samhsa.consent2share.service.dto.PatientProfileDto)
	 */
	@Override
	@Transactional
	public void updatePatient(PatientProfileDto patientProfileDto)
			throws AuthenticationFailedException {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String username = patientProfileDto.getUsername();
		if (!currentUser.getUsername().equals(username))
			throw new AuthenticationFailedException(
					"Username does not match current active user.");
		Users user = usersRepository.loadUserByUsername(username);
		if (user != null)
			if (!passwordEncoder.matches(patientProfileDto.getPassword(),
					user.getPassword()))
				throw new AuthenticationFailedException(
						"Password is incorrect.");
		logger.info("{} being run...", "updatePatient");
		Patient initialpatient = patientRepository.findByUsername(username);
		patientProfileDto.setEnterpriseIdentifier(initialpatient
				.getEnterpriseIdentifier());
		patientProfileDto.setMedicalRecordNumber(initialpatient
				.getMedicalRecordNumber());
		Patient patient = patientProfileDtoToPatientMapper
				.map(patientProfileDto);
		patientRepository.save(patient);
		
		spiritQueryService.updatePatient(patientProfileDto);

		try {
			emailSender.sendMessage(patientProfileDto.getFirstName() + " "
					+ patientProfileDto.getLastName(),
					patientProfileDto.getEmail(),
					EmailType.USER_PROFILE_CHANGE, null, null);
		} catch (MessagingException e) {
			logger.warn("Error when sending the email message.");
		}
	}
}
