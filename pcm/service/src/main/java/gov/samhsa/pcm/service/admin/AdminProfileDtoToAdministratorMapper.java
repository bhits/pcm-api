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
package gov.samhsa.pcm.service.admin;

import gov.samhsa.pcm.domain.reference.AdministrativeGenderCode;
import gov.samhsa.pcm.domain.reference.AdministrativeGenderCodeRepository;
import gov.samhsa.pcm.domain.staff.Staff;
import gov.samhsa.pcm.domain.staff.StaffRepository;
import gov.samhsa.pcm.service.dto.AdminProfileDto;

import org.springframework.util.StringUtils;

/**
 * The Class AdminProfileDtoToAdministratorMapper.
 */
public class AdminProfileDtoToAdministratorMapper {

	/** The provider admin repository. */
	StaffRepository providerAdminRepository;

	/** The administrative gender code repository. */
	AdministrativeGenderCodeRepository administrativeGenderCodeRepository;

	/**
	 * Instantiates a new admin profile dto to administrator mapper.
	 *
	 * @param providerAdminRepository
	 *            the provider admin repository
	 * @param administrativeGenderCodeRepository
	 *            the administrative gender code repository
	 */
	public AdminProfileDtoToAdministratorMapper(
			StaffRepository providerAdminRepository,
			AdministrativeGenderCodeRepository administrativeGenderCodeRepository) {
		super();
		this.providerAdminRepository = providerAdminRepository;
		this.administrativeGenderCodeRepository = administrativeGenderCodeRepository;
	}

	/**
	 * Map.
	 *
	 * @param adminProfileDto
	 *            the admin profile dto
	 * @return the staff
	 */
	public Staff map(AdminProfileDto adminProfileDto) {
		Staff administrator = null;

		// Since username is not required, so need to check if it is null
		if (adminProfileDto.getUsername() == null) {
			if (adminProfileDto.getId() != null) {
				administrator = providerAdminRepository.findOne(adminProfileDto
						.getId());
			} else {
				administrator = new Staff();
			}
		} else {
			administrator = providerAdminRepository
					.findByUsername(adminProfileDto.getUsername());
		}
		administrator.setFirstName(adminProfileDto.getFirstName());
		administrator.setLastName(adminProfileDto.getLastName());
		administrator.setEmail(adminProfileDto.getEmail());
		administrator.setEmployeeID(adminProfileDto.getEmployeeId());

		if (StringUtils.hasText(adminProfileDto.getAdministrativeGenderCode())) {
			AdministrativeGenderCode administrativeGenderCode = administrativeGenderCodeRepository
					.findByCode(adminProfileDto.getAdministrativeGenderCode());
			administrator.setAdministrativeGenderCode(administrativeGenderCode);
		} else {
			administrator.setAdministrativeGenderCode(null);
		}
		return administrator;
	}
}
