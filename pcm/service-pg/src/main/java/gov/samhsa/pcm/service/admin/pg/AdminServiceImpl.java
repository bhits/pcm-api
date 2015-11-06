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
package gov.samhsa.pcm.service.admin.pg;

import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.domain.account.EmailTokenRepository;
import gov.samhsa.pcm.domain.account.TokenGenerator;
import gov.samhsa.pcm.domain.account.UsersRepository;
import gov.samhsa.pcm.domain.commondomainservices.EmailSender;
import gov.samhsa.pcm.domain.patient.Patient;
import gov.samhsa.pcm.domain.patient.PatientRepository;
import gov.samhsa.pcm.domain.reference.AdministrativeGenderCode;
import gov.samhsa.pcm.domain.reference.AdministrativeGenderCodeRepository;
import gov.samhsa.pcm.domain.staff.StaffRepository;
import gov.samhsa.pcm.infrastructure.DtoToDomainEntityMapper;
import gov.samhsa.pcm.infrastructure.PixService;
import gov.samhsa.pcm.service.account.MrnService;
import gov.samhsa.pcm.service.account.pg.PatientExistingException;
import gov.samhsa.pcm.service.admin.AdminProfileDtoToAdministratorMapper;
import gov.samhsa.pcm.service.dto.AdminCreatePatientResponseDto;
import gov.samhsa.pcm.service.dto.BasicPatientAccountDto;
import gov.samhsa.pcm.service.dto.PatientProfileDto;
import gov.samhsa.pcm.service.spirit.SpiritClientNotAvailableException;
import gov.samhsa.pcm.service.spirit.SpiritQueryService;
import gov.samhsa.pcm.service.util.TypeConverter;
import gov.samhsa.spirit.wsclient.dto.PatientDto;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * The Class AdminServiceImpl.
 */
@Transactional
public class AdminServiceImpl extends
		gov.samhsa.pcm.service.admin.AdminServiceImpl {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The spirit query service. */
	private SpiritQueryService spiritQueryService;

	/** The patient dto to patient mapper. */
	private DtoToDomainEntityMapper<PatientDto, Patient> patientDtoToPatientMapper;

	/**
	 * Instantiates a new admin service impl.
	 *
	 * @param accountVerificationTokenExpireInHours
	 *            the account verification token expire in hours
	 * @param administratorRepository
	 *            the administrator repository
	 * @param patientRepository
	 *            the patient repository
	 * @param patientProfileDtoToPatientMapper
	 *            the patient profile dto to patient mapper
	 * @param adminProfileDtoToAdministratorMapper
	 *            the admin profile dto to administrator mapper
	 * @param modelMapper
	 *            the model mapper
	 * @param userContext
	 *            the user context
	 * @param passwordEncoder
	 *            the password encoder
	 * @param emailSender
	 *            the email sender
	 * @param tokenGenerator
	 *            the token generator
	 * @param emailTokenRepository
	 *            the email token repository
	 * @param usersRepository
	 *            the users repository
	 * @param administrativeGenderCodeRepository
	 *            the administrative gender code repository
	 * @param spiritQueryService
	 *            the spirit query service
	 * @param patientDtoToPatientMapper
	 *            the patient dto to patient mapper
	 * @param mrnService
	 *            the mrn service
	 * @param pixService
	 *            the pix service
	 */
	public AdminServiceImpl(
			Integer accountVerificationTokenExpireInHours,
			StaffRepository administratorRepository,
			PatientRepository patientRepository,
			DtoToDomainEntityMapper<PatientProfileDto, Patient> patientProfileDtoToPatientMapper,
			AdminProfileDtoToAdministratorMapper adminProfileDtoToAdministratorMapper,
			ModelMapper modelMapper,
			UserContext userContext,
			PasswordEncoder passwordEncoder,
			EmailSender emailSender,
			TokenGenerator tokenGenerator,
			EmailTokenRepository emailTokenRepository,
			UsersRepository usersRepository,
			AdministrativeGenderCodeRepository administrativeGenderCodeRepository,
			SpiritQueryService spiritQueryService,
			DtoToDomainEntityMapper<PatientDto, Patient> patientDtoToPatientMapper,
			MrnService mrnService, PixService pixService) {
		super(accountVerificationTokenExpireInHours, administratorRepository,
				patientRepository, patientProfileDtoToPatientMapper,
				adminProfileDtoToAdministratorMapper, modelMapper, userContext,
				passwordEncoder, emailSender, tokenGenerator,
				emailTokenRepository, usersRepository,
				administrativeGenderCodeRepository, mrnService, pixService);
		this.spiritQueryService = spiritQueryService;
		this.patientDtoToPatientMapper = patientDtoToPatientMapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.admin.AdminServiceImpl#updatePatient
	 * (gov.samhsa.consent2share.service.dto.PatientProfileDto)
	 */
	@Override
	@Transactional
	public void updatePatient(PatientProfileDto patientProfileDto) {
		Patient patient = patientProfileDtoToPatientMapper
				.map(patientProfileDto);
		patientRepository.save(patient);
		spiritQueryService.updatePatient(patientProfileDto);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.admin.AdminServiceImpl#createPatientAccount
	 * (gov.samhsa.consent2share.service.dto.BasicPatientAccountDto)
	 */
	@Override
	public AdminCreatePatientResponseDto createPatientAccount(
			BasicPatientAccountDto basicPatientAccountDto) {
		final String mrn = generateMrn();

		// check for duplicate patient within the source system(c2s)
		boolean isDuplicate = isDuplicatPatientByBasicDemoGraphics(basicPatientAccountDto);

		if (isDuplicate)
			throw new PatientExistingException(
					"Patient with given demographics already exists in C2S");

		Patient patient = mapBasicPatientAccountDtoToPatient(mrn,
				basicPatientAccountDto);
		AdministrativeGenderCode administrativeGenderCode = administrativeGenderCodeRepository
				.findByCode(basicPatientAccountDto
						.getAdministrativeGenderCode());
		patient.setSocialSecurityNumber(basicPatientAccountDto.getSocialSecurityNumber());	
		patient.setAdministrativeGenderCode(administrativeGenderCode);
		patient.setVerificationCode(UUID.randomUUID().toString()
				.substring(0, 7));
		
		patient = patientRepository.save(patient);

		PatientDto patientDto = spiritQueryService.addPatient(TypeConverter
				.basicPatientAccountDtoToPixPatientDto(basicPatientAccountDto,
						mrn));
		if (patientDto == null)
			throw new SpiritClientNotAvailableException(
					"Error when creating patient by PDQ. Spirit service not available.");
		
		AdminCreatePatientResponseDto response = null;
		response = new AdminCreatePatientResponseDto(
				AdminCreatePatientResponseDto.PATIENT_EXIST_IN_EXCHANGE,
				patient.getId());
		return response;
	}

	/**
	 * Checks if is duplicat patient by basic demo graphics.
	 *
	 * @param basicPatientAccountDto
	 *            the basic patient account dto
	 * @return true, if is duplicat patient by basic demo graphics
	 */
	private boolean isDuplicatPatientByBasicDemoGraphics(
			BasicPatientAccountDto basicPatientAccountDto) {

		AdministrativeGenderCode administrativeGenderCode = null;
		if (StringUtils.hasText(basicPatientAccountDto
				.getAdministrativeGenderCode())) {
			administrativeGenderCode = administrativeGenderCodeRepository
					.findByCode(basicPatientAccountDto
							.getAdministrativeGenderCode());
		}

		Patient dupPatient = patientRepository
				.findByFirstNameAndLastNameAndBirthDayAndSocialSecurityNumberAndAdministrativeGenderCode(
						basicPatientAccountDto.getFirstName(),
						basicPatientAccountDto.getLastName(),
						basicPatientAccountDto.getBirthDate(),
						basicPatientAccountDto.getSocialSecurityNumber(),
						administrativeGenderCode);
		if (dupPatient == null)
			return false;
		else
			return true;

	}
}
