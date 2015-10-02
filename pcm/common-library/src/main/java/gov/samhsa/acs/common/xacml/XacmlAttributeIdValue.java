package gov.samhsa.acs.common.xacml;

/**
 * The Enum XacmlAttributeIdValue.
 */
public enum XacmlAttributeIdValue implements XacmlAttributeId {

	/** The recipient npi. */
	RECIPIENT_NPI(
			"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject"),

	/** The intermediary npi. */
	INTERMEDIARY_NPI(
			"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject"),

	/** The purpose of use. */
	PURPOSE_OF_USE("urn:oasis:names:tc:xspa:1.0:subject:purposeofuse"),

	/** The action id. */
	ACTION_ID("urn:oasis:names:tc:xacml:1.0:action:action-id"),

	/** The current datetime. */
	CURRENT_DATETIME(
			"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime");

	/** The urn. */
	private final String urn;

	/**
	 * Instantiates a new xacml attribute id value.
	 *
	 * @param urn
	 *            the urn
	 */
	XacmlAttributeIdValue(String urn) {
		this.urn = urn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.common.xacml.XacmlAttributeId#getUrn()
	 */
	@Override
	public String getUrn() {
		return urn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.common.xacml.XacmlAttributeId#getXacmlAttributeIdValue()
	 */
	@Override
	public XacmlAttributeIdValue getXacmlAttributeIdValue() {
		return this;
	}

	/**
	 * The Enum Action.
	 */
	public enum Action implements XacmlAttributeId {

		/** The action id. */
		ACTION_ID(XacmlAttributeIdValue.ACTION_ID);

		/** The xacml attribute id value. */
		private final XacmlAttributeIdValue xacmlAttributeIdValue;

		/**
		 * Instantiates a new action.
		 *
		 * @param xacmlAttributeIdValue
		 *            the xacml attribute id value
		 */
		Action(XacmlAttributeIdValue xacmlAttributeIdValue) {
			this.xacmlAttributeIdValue = xacmlAttributeIdValue;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * gov.samhsa.acs.common.xacml.XacmlAttributeId#getXacmlAttributeIdValue
		 * ()
		 */
		@Override
		public XacmlAttributeIdValue getXacmlAttributeIdValue() {
			return this.xacmlAttributeIdValue;
		}
	}

	/**
	 * The Enum Environment.
	 */
	public enum Environment implements XacmlAttributeId {

		/** The current datetime. */
		CURRENT_DATETIME(XacmlAttributeIdValue.CURRENT_DATETIME);

		/** The xacml attribute id value. */
		private final XacmlAttributeIdValue xacmlAttributeIdValue;

		/**
		 * Instantiates a new environment.
		 *
		 * @param xacmlAttributeIdValue
		 *            the xacml attribute id value
		 */
		Environment(XacmlAttributeIdValue xacmlAttributeIdValue) {
			this.xacmlAttributeIdValue = xacmlAttributeIdValue;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * gov.samhsa.acs.common.xacml.XacmlAttributeId#getXacmlAttributeIdValue
		 * ()
		 */
		@Override
		public XacmlAttributeIdValue getXacmlAttributeIdValue() {
			return this.xacmlAttributeIdValue;
		}
	}

	/**
	 * The Enum Subject.
	 */
	public enum Subject implements XacmlAttributeId {

		/** The recipient npi. */
		RECIPIENT_NPI(XacmlAttributeIdValue.RECIPIENT_NPI),

		/** The intermediary npi. */
		INTERMEDIARY_NPI(XacmlAttributeIdValue.INTERMEDIARY_NPI),

		/** The purpose of use. */
		PURPOSE_OF_USE(XacmlAttributeIdValue.PURPOSE_OF_USE);

		/** The xacml attribute id value. */
		private final XacmlAttributeIdValue xacmlAttributeIdValue;

		/**
		 * Instantiates a new subject.
		 *
		 * @param xacmlAttributeIdValue
		 *            the xacml attribute id value
		 */
		Subject(XacmlAttributeIdValue xacmlAttributeIdValue) {
			this.xacmlAttributeIdValue = xacmlAttributeIdValue;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * gov.samhsa.acs.common.xacml.XacmlAttributeId#getXacmlAttributeIdValue
		 * ()
		 */
		@Override
		public XacmlAttributeIdValue getXacmlAttributeIdValue() {
			return this.xacmlAttributeIdValue;
		}
	}
}
