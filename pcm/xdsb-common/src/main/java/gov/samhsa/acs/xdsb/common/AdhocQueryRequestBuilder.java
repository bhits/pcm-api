package gov.samhsa.acs.xdsb.common;

import static java.util.stream.Collectors.toList;
import gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue.FindDocuments;
import gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue.FindSubmissionSets;
import gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue.GetDocuments;
import gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue.GetSubmissionSetAndContents;
import gov.samhsa.acs.xdsb.common.XdsbUUIDValue.StoredQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.ResponseOptionType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueListType;

import org.springframework.util.Assert;

/**
 * The Class AdhocQueryRequestBuilder.
 */
public class AdhocQueryRequestBuilder {

	/**
	 * Find documents.
	 *
	 * @return the xdsb registry request data
	 */
	public XdsbRegistryRequestData<FindDocuments> findDocuments() {
		final XdsbRegistryRequestData<FindDocuments> findDocumentsRequestData = new AdhocQueryRequestBuilder().new XdsbRegistryRequestData<FindDocuments>();
		findDocumentsRequestData
				.setStoredQuery(StoredQuery.UUID_STORED_QUERY_FIND_DOCUMENTS);
		return findDocumentsRequestData;
	}

	/**
	 * Find submission sets.
	 *
	 * @return the xdsb registry request data
	 */
	public XdsbRegistryRequestData<FindSubmissionSets> findSubmissionSets() {
		final XdsbRegistryRequestData<FindSubmissionSets> findSubmissionSetsRequestData = new AdhocQueryRequestBuilder().new XdsbRegistryRequestData<FindSubmissionSets>();
		findSubmissionSetsRequestData
				.setStoredQuery(StoredQuery.UUID_STORED_QUERY_FIND_SUBMISSION_SETS);
		return findSubmissionSetsRequestData;
	}

	/**
	 * Gets the documents.
	 *
	 * @return the documents
	 */
	public XdsbRegistryRequestData<GetDocuments> getDocuments() {
		final XdsbRegistryRequestData<GetDocuments> getDocumentsRequestData = new AdhocQueryRequestBuilder().new XdsbRegistryRequestData<GetDocuments>();
		getDocumentsRequestData
				.setStoredQuery(StoredQuery.UUID_STORED_QUERY_GET_DOCUMENTS);
		return getDocumentsRequestData;
	}

	/**
	 * Gets the submission set and contents.
	 *
	 * @return the submission set and contents
	 */
	public XdsbRegistryRequestData<GetSubmissionSetAndContents> getSubmissionSetAndContents() {
		final XdsbRegistryRequestData<GetSubmissionSetAndContents> getSubmissionSetAndContentsRequestData = new AdhocQueryRequestBuilder().new XdsbRegistryRequestData<GetSubmissionSetAndContents>();
		getSubmissionSetAndContentsRequestData
				.setStoredQuery(StoredQuery.UUID_STORED_QUERY_GET_SUBMISSION_SET_AND_CONTENTS);
		return getSubmissionSetAndContentsRequestData;
	}

	/**
	 * The Enum ReturnType.
	 */
	public enum ReturnType {

		/** The Leaf class. */
		LeafClass,

		/** The Object ref. */
		ObjectRef;
	}

	/**
	 * The Class XdsbRegistryRequestData.
	 *
	 * @param <T>
	 *            the generic type
	 */
	public class XdsbRegistryRequestData<T extends XdsbQueryParameter> {
		// Defaults
		/** The Constant DEFAULT_RETURN_COMPOSED_OBJECTS. */
		private static final boolean DEFAULT_RETURN_COMPOSED_OBJECTS = true;

		/** The default return type. */
		private final ReturnType DEFAULT_RETURN_TYPE = ReturnType.LeafClass;

		/** The return composed objects. */
		private boolean returnComposedObjects;

		/** The return type. */
		private ReturnType returnType;

		/** The stored query. */
		private StoredQuery storedQuery;

		/** The query parameters. */
		private final Map<T, String> queryParameters;

		/**
		 * Instantiates a new xdsb registry request data.
		 */
		private XdsbRegistryRequestData() {
			this.returnComposedObjects = DEFAULT_RETURN_COMPOSED_OBJECTS;
			this.returnType = DEFAULT_RETURN_TYPE;
			this.queryParameters = new HashMap<>();
		}

		/**
		 * Adds the query parameter.
		 *
		 * @param parameter
		 *            the parameter
		 * @param value
		 *            the value
		 * @return the xdsb registry request data
		 */
		public XdsbRegistryRequestData<T> addQueryParameter(T parameter,
				String value) {
			this.queryParameters.put(parameter, value);
			return this;
		}

		/**
		 * Builds the.
		 *
		 * @return the adhoc query request
		 */
		public AdhocQueryRequest build() {
			Assert.notNull(this.returnType);
			Assert.notNull(this.storedQuery);
			Assert.notEmpty(this.queryParameters);
			final AdhocQueryRequest request = new AdhocQueryRequest();
			final AdhocQueryType adhocQueryType = new AdhocQueryType();
			request.setAdhocQuery(adhocQueryType);
			final ResponseOptionType responseOptionType = new ResponseOptionType();
			responseOptionType
					.setReturnComposedObjects(this.returnComposedObjects);
			responseOptionType.setReturnType(this.returnType.toString());
			request.setResponseOption(responseOptionType);
			adhocQueryType.setId(this.storedQuery.getUUID());
			final List<SlotType1> slots = queryParameters.entrySet().stream()
					.map(this::toSlot).collect(toList());
			adhocQueryType.getSlot().addAll(slots);
			return request;
		}

		/**
		 * Sets the return composed objects.
		 *
		 * @param returnComposedObjects
		 *            the return composed objects
		 * @return the xdsb registry request data
		 */
		public XdsbRegistryRequestData<T> setReturnComposedObjects(
				boolean returnComposedObjects) {
			this.returnComposedObjects = returnComposedObjects;
			return this;
		}

		/**
		 * Sets the return type.
		 *
		 * @param returnType
		 *            the return type
		 * @return the xdsb registry request data
		 */
		public XdsbRegistryRequestData<T> setReturnType(ReturnType returnType) {
			this.returnType = returnType;
			return this;
		}

		/**
		 * Sets the stored query.
		 *
		 * @param storedQuery
		 *            the stored query
		 * @return the xdsb registry request data
		 */
		public XdsbRegistryRequestData<T> setStoredQuery(StoredQuery storedQuery) {
			this.storedQuery = storedQuery;
			return this;
		}

		/**
		 * To slot.
		 *
		 * @param entry
		 *            the entry
		 * @return the slot type1
		 */
		private final SlotType1 toSlot(Entry<T, String> entry) {
			final SlotType1 slot = new SlotType1();
			slot.setName(entry.getKey().getParameterName());
			final ValueListType valueList = new ValueListType();
			valueList.getValue().add(entry.getValue());
			slot.setValueList(valueList);
			return slot;
		}
	}
}
