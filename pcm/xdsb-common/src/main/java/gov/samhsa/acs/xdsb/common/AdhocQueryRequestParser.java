package gov.samhsa.acs.xdsb.common;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;

/**
 * The Class AdhocQueryRequestParser.
 */
public class AdhocQueryRequestParser {

	/**
	 * Parses the request parameter.
	 *
	 * @param req
	 *            the req
	 * @param queryParameter
	 *            the query parameter
	 * @return the optional
	 */
	public Optional<String> parseRequestParameter(AdhocQueryRequest req,
			XdsbQueryParameter queryParameter) {
		return Optional
				.ofNullable(req)
				.map(AdhocQueryRequest::getAdhocQuery)
				.map(AdhocQueryType::getSlot)
				.map(List::stream)
				.map(stream -> stream.filter(slot -> queryParameter
						.getParameterName().equals(slot.getName())))
				.map(stream -> stream.flatMap(slots -> slots.getValueList()
						.getValue().stream()
						.filter(value -> value instanceof String)))
				.map(stream -> stream.map(String.class::cast))
				.flatMap(Stream::findAny);
	}
}
