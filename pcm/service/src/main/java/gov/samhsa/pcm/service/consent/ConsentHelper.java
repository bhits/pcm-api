package gov.samhsa.pcm.service.consent;

import gov.samhsa.pcm.domain.consent.Consent;
import gov.samhsa.pcm.domain.consent.ConsentIndividualProviderDisclosureIsMadeTo;
import gov.samhsa.pcm.domain.consent.ConsentIndividualProviderPermittedToDisclose;
import gov.samhsa.pcm.domain.consent.ConsentOrganizationalProviderDisclosureIsMadeTo;
import gov.samhsa.pcm.domain.consent.ConsentOrganizationalProviderPermittedToDisclose;
import gov.samhsa.pcm.domain.consent.ConsentShareForPurposeOfUseCode;
import gov.samhsa.pcm.service.dto.ConsentDto;
import gov.samhsa.pcm.service.dto.ConsentValidationDto;
import gov.samhsa.pcm.service.dto.SpecificMedicalInfoDto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.joda.time.Interval;

/**
 * The Class ConsentHelper.
 */
public class ConsentHelper {

	/**
	 * Sets the date as end of day.
	 * 
	 * @param consentEnd
	 *            the consent end
	 * @return the date
	 */
	public Date setDateAsEndOfDay(Date consentEnd) {
		Calendar date = Calendar.getInstance();
		date.setTime(consentEnd);

		date.set(Calendar.HOUR_OF_DAY, 23);
		date.set(Calendar.MINUTE, 59);
		date.set(Calendar.SECOND, 59);

		return date.getTime();
	}

	public boolean isConsentTermOverlap(ConsentDto consentDto,
			Date conStartDate, Date conEndDate) {

		boolean isOverlap = false;
		Date selStartDate = consentDto.getConsentStart();
		Date selEndDate = consentDto.getConsentEnd();
		isOverlap = isDatesOverlap(selStartDate, selEndDate, conStartDate,
				conEndDate);

		return isOverlap;
	}

	public Set<SpecificMedicalInfoDto> getDoNotShareClinicalConceptCodes(
			HashSet<String> icd9) {
		Set<SpecificMedicalInfoDto> doNotShareClinicalConceptCodes = new HashSet<SpecificMedicalInfoDto>();
		for (String item : icd9) {
			String icd9Item = item.replace("^^^", ",");
			SpecificMedicalInfoDto specificMedicalInfoDto = new SpecificMedicalInfoDto();
			specificMedicalInfoDto.setCodeSystem("ICD9");

			specificMedicalInfoDto.setCode(icd9Item.substring(0,
					icd9Item.indexOf(";")));
			specificMedicalInfoDto.setDisplayName(icd9Item.substring(icd9Item
					.indexOf(";") + 1));
			doNotShareClinicalConceptCodes.add(specificMedicalInfoDto);
		}
		return doNotShareClinicalConceptCodes;

	}

	// Returns true is selected date range(selStartDate,selEndDate) overlaps
	// with actual data range(actStartDate,actEndDate)
	protected boolean isDatesOverlap(Date selStartDate, Date selEndDate,
			Date actStartDate, Date actEndDate) {
		boolean isOverlap = false;
		Interval selInterval = new Interval(selStartDate.getTime(),
				selEndDate.getTime());
		Interval actInterval = new Interval(actStartDate.getTime(),
				actEndDate.getTime());
		isOverlap = selInterval.overlaps(actInterval);
		return isOverlap;
	}

	public boolean isPOUMatches(Set<String> selPOU,
			Set<ConsentShareForPurposeOfUseCode> conPOU) {
		boolean isContain = false;
		// Set<ConsentShareForPurposeOfUseCode> selPOU = new
		// HashSet<ConsentShareForPurposeOfUseCode>();

		if ((null != selPOU && selPOU.size() > 0)
				&& (null != conPOU && conPOU.size() > 0)) {
			for (ConsentShareForPurposeOfUseCode aPOU : conPOU) {
				String code = aPOU.getPurposeOfUseCode().getCode();
				if (null != code && selPOU.contains(code)) {
					isContain = true;
					break;
				}
			}
		}
		return isContain;
	}

	public boolean isProviderComboMatch(Consent consent, ConsentDto consentDto) {
		boolean isContain = false;

		/** The organizational providers permitted to disclose. */
		/** The providers permitted to disclose. */
		Set<String> selToDiscloseNpi = getDtoProviderToDiscloseToNpi(consentDto);
		Set<String> conToDiscloseNpi = getProviderToDiscloseToNpi(consent);

		Set<String> selToDiscloseNpiCopy = new HashSet<String>(selToDiscloseNpi);
		Set<String> conToDiscloseNpiCopy = new HashSet<String>(conToDiscloseNpi);
		
		// check for common elements
		selToDiscloseNpiCopy.retainAll(conToDiscloseNpiCopy);

		if (null != selToDiscloseNpiCopy && selToDiscloseNpiCopy.size() > 0) {
			// match found
			/** The organizational providers disclosure is made to. */
			/** The providers disclosure is made to. */
			Set<String> selIsMadeToNpi = getDtoProviderIsMadeToNpi(consentDto);
			Set<String> conIsMadeToNpi = getProviderIsMadeToNpi(consent);
			
			Set<String> selIsMadeToNpiCopy = new HashSet<String>(selIsMadeToNpi);
			Set<String> conIsMadeToNpiCopy = new HashSet<String>(conIsMadeToNpi);

			// check for common elements
			selIsMadeToNpiCopy.retainAll(conIsMadeToNpiCopy);
			if (null != selIsMadeToNpiCopy && selIsMadeToNpiCopy.size() > 0) {
				// match found
				isContain = true;
			}
		}
		return isContain;
		
		
	}

	protected ConsentValidationDto convertConsentToConsentListDto(
			Consent consent, ConsentDto consentDto) {
		ConsentValidationDto consentValidationDto = new ConsentValidationDto();
		// Get provider fields
		// disclosure is made to
		Set<String> isMadeToName = getProviderIsMadeToName(consent);

		// permitted to disclose
		Set<String> toDiscloseName = getProviderToDiscloseToName(consent);

		consentValidationDto.setExistingConsentId(String.valueOf(consent
				.getId()));
		// consentValidationDto.setExistingConsentStatus(consent.get);
		consentValidationDto.setExistingDiscloseToProviders(isMadeToName);
		consentValidationDto.setExistingAuthorizedProviders(toDiscloseName);

		/*consentValidationDto
				.setSelectedDiscloseToProviders(getSelectedProviderIsMadeToName(consentDto));
		consentValidationDto
				.setSelectedAuthorizedProviders(getSelectedAuthorizedProviders(consentDto));*/

		// pou
		Set<String> consentShareForPurposeOfUseCode = new HashSet<String>();
		for (ConsentShareForPurposeOfUseCode item : consent
				.getShareForPurposeOfUseCodes()) {
			consentShareForPurposeOfUseCode.add(item.getPurposeOfUseCode()
					.getDisplayName());
		}
		consentValidationDto
				.setExistingPurposeOfUse(consentShareForPurposeOfUseCode);

		consentValidationDto.setSelectedPurposeOfUse(consentDto
				.getSharedPurposeNames());

		// consent term
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

		consentValidationDto.setExistingConsentStartDate(formatter
				.format(consent.getStartDate()));
		consentValidationDto.setExistingConsentEndDate(formatter.format(consent
				.getEndDate()));

		consentValidationDto.setSelectedConsentStartDate(formatter
				.format(consentDto.getConsentStart()));
		consentValidationDto.setSelectedConsentEndDate(formatter
				.format(consentDto.getConsentEnd()));

		// satus of the existing consent
		if (consent.getSignedPdfConsent() != null
				&& consent.getSignedPdfConsent().getDocumentSignedStatus()
						.equals(ConsentStatus.SIGNED)) {
			consentValidationDto
					.setExistingConsentStatus(ConsentStatus.CONSENT_SIGNED);
		} else {
			consentValidationDto
					.setExistingConsentStatus(ConsentStatus.CONSENT_SAVED);
		}

		return consentValidationDto;

	}

	protected Set<String> getSelectedProviderIsMadeToName(ConsentDto consentDto) {

		Set<String> madeToSet = new HashSet<String>();
		int sizeOfPMadeTo = 0;

		if (consentDto.getProvidersDisclosureIsMadeToNpi() != null)
			sizeOfPMadeTo = sizeOfPMadeTo
					+ consentDto.getProvidersDisclosureIsMadeToNpi().size();
		if (consentDto.getOrganizationalProvidersDisclosureIsMadeToNpi() != null)
			sizeOfPMadeTo = sizeOfPMadeTo
					+ consentDto.getOrganizationalProvidersDisclosureIsMadeToNpi()
							.size();

		if (consentDto.getMadeToNames().size() == 2 && sizeOfPMadeTo == 1) {
			Iterator it = consentDto.getMadeToNames().iterator();
			String a = it.next() + "," + it.next();
			madeToSet.add(a);

		} else {
			madeToSet = consentDto.getMadeToNames();
		}

		return madeToSet;

	}

	protected Set<String> getSelectedAuthorizedProviders(ConsentDto consentDto) {

		Set<String> madeToSet = new HashSet<String>();
		int sizeOfAuthorizedProviders = 0;

		if (consentDto.getProvidersPermittedToDiscloseNpi() != null)
			sizeOfAuthorizedProviders = sizeOfAuthorizedProviders
					+ consentDto.getProvidersPermittedToDiscloseNpi().size();
		if (consentDto.getOrganizationalProvidersPermittedToDiscloseNpi() != null)
			sizeOfAuthorizedProviders = sizeOfAuthorizedProviders
					+ consentDto
							.getOrganizationalProvidersPermittedToDiscloseNpi()
							.size();

		if (consentDto.getAuthorizerNames().size() == 2
				&& sizeOfAuthorizedProviders == 1) {
			Iterator it = consentDto.getAuthorizerNames().iterator();
			String a = it.next() + "," + it.next();
			madeToSet.add(a);

		} else {
			madeToSet = consentDto.getAuthorizerNames();
		}
		return madeToSet;

	}

	protected Set<String> getProviderIsMadeToName(Consent consent) {

		// Get provider fields
		Set<String> isMadeToName = new HashSet<String>();
		for (ConsentIndividualProviderDisclosureIsMadeTo item : consent
				.getProvidersDisclosureIsMadeTo()) {
			String name = item.getIndividualProvider().getLastName() + ", "
					+ item.getIndividualProvider().getFirstName();
			isMadeToName.add(name);
		}
		Set<String> isMadeToOrgName = new HashSet<String>();
		for (ConsentOrganizationalProviderDisclosureIsMadeTo item : consent
				.getOrganizationalProvidersDisclosureIsMadeTo()) {
			isMadeToOrgName.add(item.getOrganizationalProvider().getOrgName());
		}

		Set<String> isMadeToNameCopy = new HashSet<String>(isMadeToName);
		Set<String> isMadeToOrgNameCopy = new HashSet<String>(isMadeToOrgName);

		// Set fields
		isMadeToNameCopy.addAll(isMadeToOrgNameCopy);

		return isMadeToNameCopy;

	}

	protected Set<String> getProviderToDiscloseToName(Consent consent) {

		Set<String> toDiscloseName = new HashSet<String>();
		for (ConsentIndividualProviderPermittedToDisclose item : consent
				.getProvidersPermittedToDisclose()) {
			String name = item.getIndividualProvider().getLastName() + ", "
					+ item.getIndividualProvider().getFirstName();
			toDiscloseName.add(name);
		}

		Set<String> toDiscloseOrgName = new HashSet<String>();
		for (ConsentOrganizationalProviderPermittedToDisclose item : consent
				.getOrganizationalProvidersPermittedToDisclose()) {
			toDiscloseOrgName
					.add(item.getOrganizationalProvider().getOrgName());
		}

		Set<String> toDiscloseNameCopy = new HashSet<String>(toDiscloseName);
		Set<String> toDiscloseOrgNameCopy = new HashSet<String>(
				toDiscloseOrgName);

		// Set fields
		toDiscloseNameCopy.addAll(toDiscloseOrgNameCopy);

		return toDiscloseNameCopy;
	}

	protected Set<String> getProviderIsMadeToNpi(Consent consent) {

		// Get provider fields
		Set<String> isMadeToName = new HashSet<String>();
		for (ConsentIndividualProviderDisclosureIsMadeTo item : consent
				.getProvidersDisclosureIsMadeTo()) {
			String name = item.getIndividualProvider().getNpi();
			isMadeToName.add(name);
		}
		Set<String> isMadeToOrgName = new HashSet<String>();
		for (ConsentOrganizationalProviderDisclosureIsMadeTo item : consent
				.getOrganizationalProvidersDisclosureIsMadeTo()) {
			isMadeToOrgName.add(item.getOrganizationalProvider().getNpi());
		}

		Set<String> isMadeToNameCopy = new HashSet<String>(isMadeToName);
		Set<String> isMadeToOrgNameCopy = new HashSet<String>(isMadeToOrgName);

		// Set fields
		isMadeToNameCopy.addAll(isMadeToOrgNameCopy);

		return isMadeToNameCopy;

	}

	protected Set<String> getDtoProviderIsMadeToNpi(ConsentDto consentDto) {

		Set<String> selIsMadeToNpi = consentDto
				.getProvidersDisclosureIsMadeToNpi();
		selIsMadeToNpi = (null == selIsMadeToNpi) ? new HashSet<String>()
				: selIsMadeToNpi;

		Set<String> selIsMadeToOrgNpi = consentDto
				.getOrganizationalProvidersDisclosureIsMadeToNpi();
		selIsMadeToOrgNpi = (null == selIsMadeToOrgNpi) ? new HashSet<String>()
				: selIsMadeToOrgNpi;

		Set<String> selIsMadeToNpiCopy = new HashSet<String>(selIsMadeToNpi);
		Set<String> selIsMadeToOrgNpiCopy = new HashSet<String>(
				selIsMadeToOrgNpi);

		// Set fields
		selIsMadeToNpiCopy.addAll(selIsMadeToOrgNpiCopy);

		return selIsMadeToNpiCopy;

	}

	protected Set<String> getDtoProviderToDiscloseToNpi(ConsentDto consentDto) {
		Set<String> selToDiscloseNpi = consentDto
				.getProvidersPermittedToDiscloseNpi();
		selToDiscloseNpi = (null == selToDiscloseNpi) ? new HashSet<String>()
				: selToDiscloseNpi;
		Set<String> selToDiscloseOrgNpi = consentDto
				.getOrganizationalProvidersPermittedToDiscloseNpi();
		selToDiscloseOrgNpi = (null == selToDiscloseOrgNpi) ? new HashSet<String>()
				: selToDiscloseOrgNpi;

		Set<String> selToDiscloseNpiCopy = new HashSet<String>(selToDiscloseNpi);
		Set<String> selToDiscloseOrgNpiCopy = new HashSet<String>(
				selToDiscloseOrgNpi);

		// Set fields
		selToDiscloseNpiCopy.addAll(selToDiscloseOrgNpiCopy);
		return selToDiscloseNpiCopy;
	}

	protected Set<String> getProviderToDiscloseToNpi(Consent consent) {

		Set<String> toDiscloseName = new HashSet<String>();
		for (ConsentIndividualProviderPermittedToDisclose item : consent
				.getProvidersPermittedToDisclose()) {

			toDiscloseName.add(item.getIndividualProvider().getNpi());
		}

		Set<String> toDiscloseOrgName = new HashSet<String>();
		for (ConsentOrganizationalProviderPermittedToDisclose item : consent
				.getOrganizationalProvidersPermittedToDisclose()) {
			toDiscloseOrgName.add(item.getOrganizationalProvider().getNpi());
		}

		Set<String> toDiscloseNameCopy = new HashSet<String>(toDiscloseName);
		Set<String> toDiscloseOrgNameCopy = new HashSet<String>(
				toDiscloseOrgName);

		// Set fields
		toDiscloseNameCopy.addAll(toDiscloseOrgNameCopy);

		return toDiscloseNameCopy;
	}

	protected boolean isConsentRevoked(Consent consent) {
		boolean isRevoked = false;

		if (consent.getSignedPdfConsent() != null
				&& consent.getSignedPdfConsent().getDocumentSignedStatus()
						.equals(ConsentStatus.SIGNED)
				&& consent.getSignedPdfConsentRevoke() != null) {
			isRevoked = true;
		}
		return isRevoked;
	}
}
