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
package gov.samhsa.pcm.infrastructure.security;

import gov.samhsa.pcm.common.AuthenticatedUser;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.domain.account.Users;
import gov.samhsa.pcm.domain.account.UsersRepository;
import gov.samhsa.pcm.domain.patient.Patient;
import gov.samhsa.pcm.domain.patient.PatientRepository;
import gov.samhsa.pcm.domain.staff.Staff;
import gov.samhsa.pcm.domain.staff.StaffRepository;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * The Class UserContextImpl.
 */
public class UserContextImpl implements UserContext {

	/** The users repository. */
	private UsersRepository usersRepository;

	/** The patient repository. */
	private PatientRepository patientRepository;

	/** The provider admin repository. */
	private StaffRepository providerAdminRepository;

	/** The user details service. */
	private UserDetailsService userDetailsService;

	/**
	 * Instantiates a new user context impl.
	 *
	 * @param usersRepository
	 *            the users repository
	 * @param patientRepository
	 *            the patient repository
	 * @param providerAdminRepository
	 *            the provider admin repository
	 * @param userDetailsService
	 *            the user details service
	 */
	public UserContextImpl(UsersRepository usersRepository,
			PatientRepository patientRepository,
			StaffRepository providerAdminRepository,
			UserDetailsService userDetailsService) {
		super();
		this.usersRepository = usersRepository;
		this.patientRepository = patientRepository;
		this.providerAdminRepository = providerAdminRepository;
		this.userDetailsService = userDetailsService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.infrastructure.security.UserContext#getCurrentUser
	 * ()
	 */
	@Override
	public AuthenticatedUser getCurrentUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication == null) {
			return null;
		}

		String username = authentication.getName();

		// Load generic user object from user repository by username
		Users users = usersRepository.loadUserByUsername(username);

		// Initialize patient variable to null
		Patient patient = null;
		// Initialize providerAdmin variable to null
		Staff providerAdmin = null;

		if (users != null) {
			// Check if user is an admin or regular user
			if (users.getAuthorities().contains(
					new SimpleGrantedAuthority("ROLE_ADMIN"))) {
				// If user is an admin, then find providerAdmin by username from
				// the providerAdminRepository
				providerAdmin = providerAdminRepository
						.findByUsername(username);
				patient = null;
			} else if (users.getAuthorities().contains(
					new SimpleGrantedAuthority("ROLE_USER"))) {
				// If user is a regular user (i.e. patient), then find patient
				// by username from the patientRepository
				patient = patientRepository.findByUsername(username);
				providerAdmin = null;
			}
		}

		// Initialize new AuthenticatedUser
		AuthenticatedUser authenticatedUser = new AuthenticatedUser();
		authenticatedUser.setUsername(username);

		/*
		 * Set data for authenticatedUser based on user role (providerAdmin or
		 * patient)
		 * 
		 * patient != null --- user is a patient providerAdmin != null --- user
		 * is a providerAdmin
		 */
		if (patient != null) {
			// Set authenticatedUser data based on data from patient variable
			// (user is a patient)
			authenticatedUser.setFirstName(patient.getFirstName());
			authenticatedUser.setLastName(patient.getLastName());
			authenticatedUser.setBirthDate(patient.getBirthDay());
			authenticatedUser.setGenderDisplayName(patient
					.getAdministrativeGenderCode().getDisplayName());
			authenticatedUser.setIsProviderAdmin(false);
		} else if (providerAdmin != null) {
			// Set authenticatedUser data based on data from providerAdmin
			// variable (user is a providerAdmin)
			authenticatedUser.setFirstName(providerAdmin.getFirstName());
			authenticatedUser.setLastName(providerAdmin.getLastName());
			authenticatedUser.setGenderDisplayName(providerAdmin
					.getAdministrativeGenderCode().getDisplayName());
			authenticatedUser.setIsProviderAdmin(true);
		}

		return authenticatedUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.infrastructure.security.UserContext#setCurrentUser
	 * (java.lang.String)
	 */
	@Override
	public void setCurrentUser(String username) {

		UserDetails userDetails = null;
		try {
			userDetails = userDetailsService.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {

		}

		if (userDetails != null) {
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					userDetails, "", userDetails.getAuthorities());
			SecurityContextHolder.getContext()
					.setAuthentication(authentication);
		}
	}
}
