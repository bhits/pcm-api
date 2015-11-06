package gov.samhsa.pcm.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ConsentValidationDto implements Serializable{
	
	private static final long serialVersionUID = -4576727893695238310L;
	private String notificationMsg;
	private Set<String> selectedAuthorizedProviders = new HashSet<String>();
	private Set<String> selectedDiscloseToProviders = new HashSet<String>();
	private Set<String> selectedPurposeOfUse = new HashSet<String>();
	private String selectedConsentStartDate;
	private String selectedConsentEndDate;
	private Set<String> existingAuthorizedProviders = new HashSet<String>();
	private Set<String> existingDiscloseToProviders = new HashSet<String>();
	private Set<String> existingPurposeOfUse = new HashSet<String>();
	private String existingConsentStartDate;
	private String existingConsentEndDate;
	private String existingConsentStatus;
	private String existingConsentId;
	public String getNotificationMsg() {
		return notificationMsg;
	}
	public void setNotificationMsg(String notificationMsg) {
		this.notificationMsg = notificationMsg;
	}
	public Set<String> getSelectedAuthorizedProviders() {
		return selectedAuthorizedProviders;
	}
	public void setSelectedAuthorizedProviders(
			Set<String> selectedAuthorizedProviders) {
		this.selectedAuthorizedProviders = selectedAuthorizedProviders;
	}
	public Set<String> getSelectedDiscloseToProviders() {
		return selectedDiscloseToProviders;
	}
	public void setSelectedDiscloseToProviders(
			Set<String> selectedDiscloseToProviders) {
		this.selectedDiscloseToProviders = selectedDiscloseToProviders;
	}
	public Set<String> getSelectedPurposeOfUse() {
		return selectedPurposeOfUse;
	}
	public void setSelectedPurposeOfUse(Set<String> selectedPurposeOfUse) {
		this.selectedPurposeOfUse = selectedPurposeOfUse;
	}
	public String getSelectedConsentStartDate() {
		return selectedConsentStartDate;
	}
	public void setSelectedConsentStartDate(String selectedConsentStartDate) {
		this.selectedConsentStartDate = selectedConsentStartDate;
	}
	public String getSelectedConsentEndDate() {
		return selectedConsentEndDate;
	}
	public void setSelectedConsentEndDate(String selectedConsentEndDate) {
		this.selectedConsentEndDate = selectedConsentEndDate;
	}
	public Set<String> getExistingAuthorizedProviders() {
		return existingAuthorizedProviders;
	}
	public void setExistingAuthorizedProviders(
			Set<String> existingAuthorizedProviders) {
		this.existingAuthorizedProviders = existingAuthorizedProviders;
	}
	public Set<String> getExistingDiscloseToProviders() {
		return existingDiscloseToProviders;
	}
	public void setExistingDiscloseToProviders(
			Set<String> existingDiscloseToProviders) {
		this.existingDiscloseToProviders = existingDiscloseToProviders;
	}
	public Set<String> getExistingPurposeOfUse() {
		return existingPurposeOfUse;
	}
	public void setExistingPurposeOfUse(Set<String> existingPurposeOfUse) {
		this.existingPurposeOfUse = existingPurposeOfUse;
	}
	public String getExistingConsentStartDate() {
		return existingConsentStartDate;
	}
	public void setExistingConsentStartDate(String existingConsentStartDate) {
		this.existingConsentStartDate = existingConsentStartDate;
	}
	public String getExistingConsentEndDate() {
		return existingConsentEndDate;
	}
	public void setExistingConsentEndDate(String existingConsentEndDate) {
		this.existingConsentEndDate = existingConsentEndDate;
	}
	public String getExistingConsentStatus() {
		return existingConsentStatus;
	}
	public void setExistingConsentStatus(String existingConsentStatus) {
		this.existingConsentStatus = existingConsentStatus;
	}
	public String getExistingConsentId() {
		return existingConsentId;
	}
	public void setExistingConsentId(String existingConsentId) {
		this.existingConsentId = existingConsentId;
	}

	

	
}


