package gov.va.ds4p.cas;

public class RuleExecutionResponse {
	private Confidentiality ImpliedConfSection;
	private String Sensitivity;
	private String ItemAction;
	private String USPrivacyLaw;
	private String DocumentObligationPolicy;
	private String DocumentRefrainPolicy;
	private String c32SectionTitle;
	private String c32SectionLoincCode;
	private String observationId;
	private String code;
	private String displayName;
	private String codeSystemName;

	public Confidentiality getImpliedConfSection() {
		return this.ImpliedConfSection;
	}

	public void setImpliedConfSection(Confidentiality ImpliedConfSection) {
		this.ImpliedConfSection = ImpliedConfSection;
	}

	public String getUSPrivacyLaw() {
		return this.USPrivacyLaw;
	}

	public void setUSPrivacyLaw(String USPrivacyLaw) {
		this.USPrivacyLaw = USPrivacyLaw;
	}

	public String getDocumentObligationPolicy() {
		return this.DocumentObligationPolicy;
	}

	public void setDocumentObligationPolicy(String DocumentObligationPolicy) {
		this.DocumentObligationPolicy = DocumentObligationPolicy;
	}

	public String getDocumentRefrainPolicy() {
		return this.DocumentRefrainPolicy;
	}

	public void setDocumentRefrainPolicy(String DocumentRefrainPolicy) {
		this.DocumentRefrainPolicy = DocumentRefrainPolicy;
	}

	public String getC32SectionTitle() {
		return this.c32SectionTitle;
	}

	public void setC32SectionTitle(String c32SectionTitle) {
		this.c32SectionTitle = c32SectionTitle;
	}

	public String getC32SectionLoincCode() {
		return this.c32SectionLoincCode;
	}

	public void setC32SectionLoincCode(String c32SectionLoincCode) {
		this.c32SectionLoincCode = c32SectionLoincCode;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getCodeSystemName() {
		return this.codeSystemName;
	}

	public void setCodeSystemName(String codeSystemName) {
		this.codeSystemName = codeSystemName;
	}

	public RuleExecutionResponse() {
	}

	public RuleExecutionResponse(String sensitivity, String itemaction,
			String impliedconf, String usprivacylaw,
			String documentobligationpolicy, String documentrefrainpolicy,
			String c32sectiontitle, String c32sectionloinccode,
			String observationid, String code, String displayName,
			String codeSystemName) {
		this.Sensitivity = sensitivity;
		this.ItemAction = itemaction;
		if (impliedconf.equals("N"))
			this.ImpliedConfSection = Confidentiality.N;
		if (impliedconf.equals("R"))
			this.ImpliedConfSection = Confidentiality.R;
		if (impliedconf.equals("V"))
			this.ImpliedConfSection = Confidentiality.V;
		this.DocumentObligationPolicy = documentobligationpolicy;
		this.DocumentRefrainPolicy = documentrefrainpolicy;
		this.USPrivacyLaw = usprivacylaw;
		this.c32SectionLoincCode = c32sectionloinccode;
		this.c32SectionTitle = c32sectiontitle;
		this.observationId = observationid;
		this.code = code;
		this.displayName = displayName;
		this.codeSystemName = codeSystemName;
	}

	public String getSensitivity() {
		return this.Sensitivity;
	}

	public void setSensitivity(String Sensitivity) {
		this.Sensitivity = Sensitivity;
	}

	public String getItemAction() {
		return this.ItemAction;
	}

	public void setItemAction(String ItemAction) {
		this.ItemAction = ItemAction;
	}

	public String getObservationId() {
		return this.observationId;
	}

	public void setObservationId(String observationId) {
		this.observationId = observationId;
	}

	public static enum Confidentiality {
		V(3), R(2), N(1);

		private int priority;

		private Confidentiality(int p) {
			this.priority = p;
		}

		int getPriority() {
			return this.priority;
		}
	}
}
