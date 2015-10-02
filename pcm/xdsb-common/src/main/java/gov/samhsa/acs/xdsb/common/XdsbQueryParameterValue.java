package gov.samhsa.acs.xdsb.common;

/**
 * The Enum XdsbQueryParameterValue.
 */
public enum XdsbQueryParameterValue implements XdsbQueryParameter {

	/** The xds document entry patient id. */
	XDS_DOCUMENT_ENTRY_PATIENT_ID("$XDSDocumentEntryPatientId"),

	/** The xds document entry status. */
	XDS_DOCUMENT_ENTRY_STATUS("$XDSDocumentEntryStatus"),

	/** The xds document entry service start time to. */
	XDS_DOCUMENT_ENTRY_SERVICE_START_TIME_TO(
			"$XDSDocumentEntryServiceStartTimeTo"),

	/** The xds document entry service stop time from. */
	XDS_DOCUMENT_ENTRY_SERVICE_STOP_TIME_FROM(
			"$XDSDocumentEntryServiceStopTimeFrom"),

	/** The xds document entry format code. */
	XDS_DOCUMENT_ENTRY_FORMAT_CODE("$XDSDocumentEntryFormatCode"),

	/** The xds document entry unique id. */
	XDS_DOCUMENT_ENTRY_UNIQUE_ID("$XDSDocumentEntryUniqueId"),

	/** The xds submission set unique id. */
	XDS_SUBMISSION_SET_UNIQUE_ID("$XDSSubmissionSetUniqueId"),

	/** The xds submission set patient id. */
	XDS_SUBMISSION_SET_PATIENT_ID("$XDSSubmissionSetPatientId"),

	/** The xds submission set author person. */
	XDS_SUBMISSION_SET_AUTHOR_PERSON("$XDSSubmissionSetAuthorPerson"),

	/** The xds submission set status. */
	XDS_SUBMISSION_SET_STATUS("$XDSSubmissionSetStatus");

	/** The parameter name. */
	private final String parameterName;

	/**
	 * Instantiates a new xdsb query parameter value.
	 *
	 * @param parameterName
	 *            the parameter name
	 */
	XdsbQueryParameterValue(String parameterName) {
		this.parameterName = parameterName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.xdsb.common.XdsbQueryParameter#getParameter()
	 */
	@Override
	public XdsbQueryParameterValue getParameter() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.xdsb.common.XdsbQueryParameter#getParameterName()
	 */
	@Override
	public String getParameterName() {
		return this.parameterName;
	}

	/**
	 * The Enum FindDocuments.
	 */
	public enum FindDocuments implements XdsbQueryParameter {

		/** The xds document entry patient id. */
		XDS_DOCUMENT_ENTRY_PATIENT_ID(
				XdsbQueryParameterValue.XDS_DOCUMENT_ENTRY_PATIENT_ID),

		/** The xds document entry status. */
		XDS_DOCUMENT_ENTRY_STATUS(
				XdsbQueryParameterValue.XDS_DOCUMENT_ENTRY_STATUS),

		/** The xds document entry service start time to. */
		XDS_DOCUMENT_ENTRY_SERVICE_START_TIME_TO(
				XdsbQueryParameterValue.XDS_DOCUMENT_ENTRY_SERVICE_START_TIME_TO),

		/** The xds document entry service stop time from. */
		XDS_DOCUMENT_ENTRY_SERVICE_STOP_TIME_FROM(
				XdsbQueryParameterValue.XDS_DOCUMENT_ENTRY_SERVICE_STOP_TIME_FROM),

		/** The xds document entry format code. */
		XDS_DOCUMENT_ENTRY_FORMAT_CODE(
				XdsbQueryParameterValue.XDS_DOCUMENT_ENTRY_FORMAT_CODE);

		/** The parameter. */
		private final XdsbQueryParameterValue parameter;

		/**
		 * Instantiates a new find documents.
		 *
		 * @param parameter
		 *            the parameter
		 */
		FindDocuments(XdsbQueryParameterValue parameter) {
			this.parameter = parameter;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see gov.samhsa.acs.xdsb.common.XdsbQueryParameter#getParameter()
		 */
		@Override
		public XdsbQueryParameterValue getParameter() {
			return this.parameter;
		}
	}

	/**
	 * The Enum FindSubmissionSets.
	 */
	public enum FindSubmissionSets implements XdsbQueryParameter {

		/** The xds submission set patient id. */
		XDS_SUBMISSION_SET_PATIENT_ID(
				XdsbQueryParameterValue.XDS_SUBMISSION_SET_PATIENT_ID),

		/** The xds submission set author person. */
		XDS_SUBMISSION_SET_AUTHOR_PERSON(
				XdsbQueryParameterValue.XDS_SUBMISSION_SET_AUTHOR_PERSON),

		/** The xds submission set status. */
		XDS_SUBMISSION_SET_STATUS(
				XdsbQueryParameterValue.XDS_SUBMISSION_SET_STATUS);

		/** The parameter. */
		private final XdsbQueryParameterValue parameter;

		/**
		 * Instantiates a new find submission sets.
		 *
		 * @param parameter
		 *            the parameter
		 */
		FindSubmissionSets(XdsbQueryParameterValue parameter) {
			this.parameter = parameter;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see gov.samhsa.acs.xdsb.common.XdsbQueryParameter#getParameter()
		 */
		@Override
		public XdsbQueryParameterValue getParameter() {
			return this.parameter;
		}
	}

	/**
	 * The Enum GetAll.
	 */
	public enum GetAll implements XdsbQueryParameter {

		/** The xds document entry format code. */
		XDS_DOCUMENT_ENTRY_FORMAT_CODE(
				XdsbQueryParameterValue.XDS_DOCUMENT_ENTRY_FORMAT_CODE),

		/** The xds document entry status. */
		XDS_DOCUMENT_ENTRY_STATUS(
				XdsbQueryParameterValue.XDS_DOCUMENT_ENTRY_STATUS),

		/** The xds submission set status. */
		XDS_SUBMISSION_SET_STATUS(
				XdsbQueryParameterValue.XDS_SUBMISSION_SET_STATUS);

		/** The parameter. */
		private final XdsbQueryParameterValue parameter;

		/**
		 * Instantiates a new gets the all.
		 *
		 * @param parameter
		 *            the parameter
		 */
		GetAll(XdsbQueryParameterValue parameter) {
			this.parameter = parameter;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see gov.samhsa.acs.xdsb.common.XdsbQueryParameter#getParameter()
		 */
		@Override
		public XdsbQueryParameterValue getParameter() {
			return this.parameter;
		}
	}

	/**
	 * The Enum GetDocuments.
	 */
	public enum GetDocuments implements XdsbQueryParameter {

		/** The xds document entry unique id. */
		XDS_DOCUMENT_ENTRY_UNIQUE_ID(
				XdsbQueryParameterValue.XDS_DOCUMENT_ENTRY_UNIQUE_ID);

		/** The parameter. */
		private XdsbQueryParameterValue parameter;

		/**
		 * Instantiates a new gets the documents.
		 *
		 * @param parameter
		 *            the parameter
		 */
		GetDocuments(XdsbQueryParameterValue parameter) {
			this.parameter = parameter;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see gov.samhsa.acs.xdsb.common.XdsbQueryParameter#getParameter()
		 */
		@Override
		public XdsbQueryParameterValue getParameter() {
			return this.parameter;
		}
	}

	/**
	 * The Enum GetSubmissionSetAndContents.
	 */
	public enum GetSubmissionSetAndContents implements XdsbQueryParameter {

		/** The xds document entry format code. */
		XDS_DOCUMENT_ENTRY_FORMAT_CODE(
				XdsbQueryParameterValue.XDS_DOCUMENT_ENTRY_FORMAT_CODE),

		/** The xds submission set unique id. */
		XDS_SUBMISSION_SET_UNIQUE_ID(
				XdsbQueryParameterValue.XDS_SUBMISSION_SET_UNIQUE_ID);

		/** The parameter. */
		private final XdsbQueryParameterValue parameter;

		/**
		 * Instantiates a new gets the submission set and contents.
		 *
		 * @param parameter
		 *            the parameter
		 */
		GetSubmissionSetAndContents(XdsbQueryParameterValue parameter) {
			this.parameter = parameter;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see gov.samhsa.acs.xdsb.common.XdsbQueryParameter#getParameter()
		 */
		@Override
		public XdsbQueryParameterValue getParameter() {
			return this.parameter;
		}
	}
}
