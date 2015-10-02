package gov.samhsa.acs.xdsb.common;

/**
 * The Enum XdsbUUIDValue.
 */
public enum XdsbUUIDValue implements XdsbUUID {
	// Stored Queries
	/** The uuid stored query find documents. */
	UUID_STORED_QUERY_FIND_DOCUMENTS(
			"urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d"),

	/** The uuid stored query find submission sets. */
	UUID_STORED_QUERY_FIND_SUBMISSION_SETS(
			"urn:uuid:f26abbcb-ac74-4422-8a30-edb644bbc1a9"),

	/** The uuid stored query get submission set and contents. */
	UUID_STORED_QUERY_GET_SUBMISSION_SET_AND_CONTENTS(
			"urn:uuid:e8e3cb2c-e39c-46b9-99e4-c12f57260b83"),

	/** The uuid stored query get documents. */
	UUID_STORED_QUERY_GET_DOCUMENTS(
			"urn:uuid:5c4f972b-d56b-40ac-a5fc-c8ca9b40b9d4"),

	// XDSDocumentEntry
	/** The Constant UUID_XDS_DOCUMENTENTRY. */
	UUID_XDS_DOCUMENTENTRY("urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1"),

	/** The Constant UUID_XDS_DOCUMENTENTRY_UNIQUEID. */
	UUID_XDS_DOCUMENTENTRY_UNIQUEID(
			"urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab"),

	/** The Constant UUID_XDS_DOCUMENTENTRY_AUTHOR. */
	UUID_XDS_DOCUMENTENTRY_AUTHOR(
			"urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d"),

	/** The uuid xds submission set uniqueid. */
	UUID_XDS_SUBMISSION_SET_UNIQUEID(
			"urn:uuid:96fdda7c-d067-4183-912e-bf5ee74998a8");

	/** The uuid. */
	private String uuid;

	/**
	 * Instantiates a new xdsb uuid value.
	 *
	 * @param uuid
	 *            the uuid
	 */
	XdsbUUIDValue(String uuid) {
		this.uuid = uuid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.xdsb.common.XdsbUUID#getUUID()
	 */
	@Override
	public String getUUID() {
		return this.uuid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.xdsb.common.XdsbUUID#getXdsbUUIDValue()
	 */
	@Override
	public XdsbUUIDValue getXdsbUUIDValue() {
		return this;
	}

	/**
	 * The Enum StoredQuery.
	 */
	public enum StoredQuery implements XdsbUUID {

		// Stored Queries
		/** The uuid stored query find documents. */
		UUID_STORED_QUERY_FIND_DOCUMENTS(
				XdsbUUIDValue.UUID_STORED_QUERY_FIND_DOCUMENTS),

		/** The uuid stored query find submission sets. */
		UUID_STORED_QUERY_FIND_SUBMISSION_SETS(
				XdsbUUIDValue.UUID_STORED_QUERY_FIND_SUBMISSION_SETS),

		/** The uuid stored query get submission set and contents. */
		UUID_STORED_QUERY_GET_SUBMISSION_SET_AND_CONTENTS(
				XdsbUUIDValue.UUID_STORED_QUERY_GET_SUBMISSION_SET_AND_CONTENTS),

		/** The uuid stored query get documents. */
		UUID_STORED_QUERY_GET_DOCUMENTS(
				XdsbUUIDValue.UUID_STORED_QUERY_GET_DOCUMENTS);

		/** The uuid. */
		private final XdsbUUIDValue uuid;

		/**
		 * Instantiates a new stored query.
		 *
		 * @param uuid
		 *            the uuid
		 */
		StoredQuery(XdsbUUIDValue uuid) {
			this.uuid = uuid;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see gov.samhsa.acs.xdsb.common.XdsbUUID#getXdsbUUIDValue()
		 */
		@Override
		public XdsbUUIDValue getXdsbUUIDValue() {
			return this.uuid;
		}
	}

	/**
	 * The Class XDSDocumentEntry.
	 */
	public enum XDSDocumentEntry implements XdsbUUID {
		// XDSDocumentEntry
		/** The Constant UUID_XDS_DOCUMENTENTRY. */
		UUID_XDS_DOCUMENTENTRY(XdsbUUIDValue.UUID_XDS_DOCUMENTENTRY),

		/** The Constant UUID_XDS_DOCUMENTENTRY_UNIQUEID. */
		UUID_XDS_DOCUMENTENTRY_UNIQUEID(
				XdsbUUIDValue.UUID_XDS_DOCUMENTENTRY_UNIQUEID),

		/** The Constant UUID_XDS_DOCUMENTENTRY_AUTHOR. */
		UUID_XDS_DOCUMENTENTRY_AUTHOR(
				XdsbUUIDValue.UUID_XDS_DOCUMENTENTRY_AUTHOR);

		/** The uuid. */
		private final XdsbUUIDValue uuid;

		/**
		 * Instantiates a new XDS document entry.
		 *
		 * @param uuid
		 *            the uuid
		 */
		XDSDocumentEntry(XdsbUUIDValue uuid) {
			this.uuid = uuid;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see gov.samhsa.acs.xdsb.common.XdsbUUID#getXdsbUUIDValue()
		 */
		@Override
		public XdsbUUIDValue getXdsbUUIDValue() {
			return this.uuid;
		}
	}

	/**
	 * The Class XDSSubmissionSet.
	 */
	public enum XDSSubmissionSet implements XdsbUUID {
		// XDSSubmissionSet
		/** The Constant UUID_XDS_SUBMISSION_SET_UNIQUEID. */
		UUID_XDS_SUBMISSION_SET_UNIQUEID(
				XdsbUUIDValue.UUID_XDS_SUBMISSION_SET_UNIQUEID);

		/** The uuid. */
		private XdsbUUIDValue uuid;

		/**
		 * Instantiates a new XDS submission set.
		 *
		 * @param uuid
		 *            the uuid
		 */
		XDSSubmissionSet(XdsbUUIDValue uuid) {
			this.uuid = uuid;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see gov.samhsa.acs.xdsb.common.XdsbUUID#getXdsbUUIDValue()
		 */
		@Override
		public XdsbUUIDValue getXdsbUUIDValue() {
			return this.uuid;
		}
	}
}
