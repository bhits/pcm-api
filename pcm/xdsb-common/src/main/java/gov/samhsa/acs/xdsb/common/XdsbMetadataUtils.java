package gov.samhsa.acs.xdsb.common;

import static gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue.FindDocuments.XDS_DOCUMENT_ENTRY_PATIENT_ID;
import static gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue.FindDocuments.XDS_DOCUMENT_ENTRY_STATUS;
import static gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue.GetAll.XDS_DOCUMENT_ENTRY_FORMAT_CODE;

import java.util.List;
import java.util.Optional;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.ResponseOptionType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueListType;

/**
 * The Class XdsbMetadataUtils.
 */
public final class XdsbMetadataUtils {

	/**
	 * Instantiates a new xdsb metadata utils.
	 */
	private XdsbMetadataUtils() {
	}

	/**
	 * Extract document entry status.
	 *
	 * @param req
	 *            the req
	 * @return the optional
	 */
	public static final Optional<String> extractDocumentEntryStatus(
			AdhocQueryRequest req) {
		return extractSlotValue(req, XDS_DOCUMENT_ENTRY_STATUS);
	}

	/**
	 * Extract format code.
	 *
	 * @param req
	 *            the req
	 * @return the optional
	 */
	public static final Optional<String> extractFormatCode(AdhocQueryRequest req) {
		return extractSlotValue(req, XDS_DOCUMENT_ENTRY_FORMAT_CODE);
	}

	/**
	 * Extract patient id.
	 *
	 * @param req
	 *            the req
	 * @return the optional
	 */
	public static final Optional<String> extractPatientId(AdhocQueryRequest req) {
		return extractSlotValue(req, XDS_DOCUMENT_ENTRY_PATIENT_ID);
	}

	/**
	 * Extract response option return type.
	 *
	 * @param req
	 *            the req
	 * @return the optional
	 */
	public static final Optional<String> extractResponseOptionReturnType(
			AdhocQueryRequest req) {
		return Optional.of(req).map(AdhocQueryRequest::getResponseOption)
				.map(ResponseOptionType::getReturnType);
	}

	/**
	 * Extract slot value.
	 *
	 * @param req
	 *            the req
	 * @param slotName
	 *            the slot name
	 * @return the optional
	 */
	public static final Optional<String> extractSlotValue(
			AdhocQueryRequest req, String slotName) {
		return Optional
				.of(req)
				.map(AdhocQueryRequest::getAdhocQuery)
				.map(AdhocQueryType::getSlot)
				.map(List::stream)
				.flatMap(
						stream -> stream.filter(
								slot -> slotName.equals(slot.getName()))
								.findAny()).map(SlotType1::getValueList)
				.map(ValueListType::getValue).filter(value -> value.size() > 0)
								.map(value -> value.get(0));
	}

	/**
	 * Extract slot value.
	 *
	 * @param req
	 *            the req
	 * @param slotName
	 *            the slot name
	 * @return the optional
	 */
	public static final Optional<String> extractSlotValue(
			AdhocQueryRequest req, XdsbQueryParameter slotName) {
		return extractSlotValue(req, slotName.getParameterName());
	}

}
