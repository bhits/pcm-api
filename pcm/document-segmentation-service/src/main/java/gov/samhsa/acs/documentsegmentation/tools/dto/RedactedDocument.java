package gov.samhsa.acs.documentsegmentation.tools.dto;

import java.util.Set;

public class RedactedDocument {
	private String redactedDocument;
	private String tryPolicyDocument;
	private Set<String> redactedSectionSet;
	private Set<String> redactedCategorySet;
	public RedactedDocument(String redactedDocument, String tryPolicyDocument,
			Set<String> redactedSectionSet, Set<String> redactedCategorySet) {
		super();
		this.redactedDocument = redactedDocument;
		this.tryPolicyDocument = tryPolicyDocument;
		this.redactedSectionSet = redactedSectionSet;
		this.redactedCategorySet = redactedCategorySet;
	}
	public String getTryPolicyDocument() {
		return tryPolicyDocument;
	}
	public void setTryPolicyDocument(String tryPolicyDocument) {
		this.tryPolicyDocument = tryPolicyDocument;
	}
	public String getRedactedDocument() {
		return redactedDocument;
	}
	public void setRedactedDocument(String redactedDocument) {
		this.redactedDocument = redactedDocument;
	}	
	public Set<String> getRedactedSectionSet() {
		return redactedSectionSet;
	}
	public void setRedactedSectionSet(Set<String> redactedSectionSet) {
		this.redactedSectionSet = redactedSectionSet;
	}
	public Set<String> getRedactedCategorySet() {
		return redactedCategorySet;
	}
	public void setRedactedCategorySet(Set<String> redactedCategorySet) {
		this.redactedCategorySet = redactedCategorySet;
	}	
}
