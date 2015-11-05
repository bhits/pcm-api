package gov.samhsa.pcm.pixclient.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.jxpath.JXPathContext;
import org.hl7.v3.types.II;
import org.hl7.v3.types.MCCIIN000002UV01;
import org.hl7.v3.types.MCCIMT000200UV01Acknowledgement;
import org.hl7.v3.types.MCCIMT000200UV01AcknowledgementDetail;
import org.hl7.v3.types.MCCIMT000300UV01Acknowledgement;
import org.hl7.v3.types.MCCIMT000300UV01AcknowledgementDetail;
import org.hl7.v3.types.PRPAIN201310UV02;
import org.hl7.v3.types.PRPAMT201307UV02PatientIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PixManagerMessageHelper.
 */
public class PixManagerMessageHelper {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Gets the adds the update message.
	 *
	 * @param response
	 *            the response
	 * @param pixMgrBean
	 *            the pix mgr bean
	 * @param msg
	 *            the msg
	 * @return the adds the update message
	 */
	public void getAddUpdateMessage(MCCIIN000002UV01 response,
			PixManagerBean pixMgrBean, String msg) {
		// The message has been sent
		// But it doesn't mean we're subscribed successfully
		logger.debug("response ack code:" + response.getAcceptAckCode());
		logger.debug("response type id: " + response.getTypeId());
		final List<MCCIMT000200UV01Acknowledgement> ackmntList = response
				.getAcknowledgement();
		for (final MCCIMT000200UV01Acknowledgement ackmnt : ackmntList) {
			if (ackmnt.getTypeCode().getCode().equals("CA")) {
				if (PixManagerConstants.PIX_ADD.equalsIgnoreCase(msg)) {
					pixMgrBean.setAddMessage(msg + " Success! ");
				} else if (PixManagerConstants.PIX_UPDATE.equalsIgnoreCase(msg)) {
					pixMgrBean.setUpdateMessage(msg + " Success! ");
				}

				break;
			} else if (ackmnt.getTypeCode().getCode().equals("CE")) {
				final List<MCCIMT000200UV01AcknowledgementDetail> ackmntDetList = ackmnt
						.getAcknowledgementDetail();
				for (final MCCIMT000200UV01AcknowledgementDetail ackDet : ackmntDetList) {
					logger.error(msg + " error : "
							+ ackDet.getText().getContent());
					if (PixManagerConstants.PIX_ADD.equalsIgnoreCase(msg)) {
						pixMgrBean.setAddMessage(msg + " Failure! "
								+ ackDet.getText().getContent());
					} else if (PixManagerConstants.PIX_UPDATE
							.equalsIgnoreCase(msg)) {
						pixMgrBean.setUpdateMessage(msg + " Failure! "
								+ ackDet.getText().getContent());
					}
					break;
				}

			} else {

				if (PixManagerConstants.PIX_ADD.equalsIgnoreCase(msg)) {
					pixMgrBean.setAddMessage(msg + " Failure! ");
				} else if (PixManagerConstants.PIX_UPDATE.equalsIgnoreCase(msg)) {
					pixMgrBean.setUpdateMessage(msg + " Failure! ");
				}
			}
		}
	}

	/**
	 * Gets the general exp message.
	 *
	 * @param e
	 *            the e
	 * @param pixMgrBean
	 *            the pix mgr bean
	 * @param msg
	 *            the msg
	 * @return the general exp message
	 */
	public void getGeneralExpMessage(Exception e, PixManagerBean pixMgrBean,
			String msg) {
		// Expect the unexpected
		logger.error("Unexpected exception", e);

		// Add error
		logger.error("error",
				"Query Failure! Server error! A unexpected error has occured");

		final String errMsg = " Failure! Server error! A unexpected error has occured";
		logger.error("exception: " + e.getCause());
		logger.error("detail message: " + e.getMessage());

		if (PixManagerConstants.PIX_ADD.equalsIgnoreCase(msg)) {
			pixMgrBean.setAddMessage(msg + errMsg);
		} else if (PixManagerConstants.PIX_QUERY.equalsIgnoreCase(msg)) {
			pixMgrBean.setQueryMessage(msg + errMsg);
		} else if (PixManagerConstants.PIX_UPDATE.equalsIgnoreCase(msg)) {
			pixMgrBean.setUpdateMessage(msg + errMsg);
		}
	}

	/**
	 * Gets the query message.
	 *
	 * @param response
	 *            the response
	 * @param pixMgrBean
	 *            the pix mgr bean
	 * @return the query message
	 */
	@SuppressWarnings("unchecked")
	public PixManagerBean getQueryMessage(PRPAIN201310UV02 response,
			PixManagerBean pixMgrBean) {

		// The message has been sent
		// But it doesn't mean we're subscribed successfully
		logger.debug("response ack code:" + response.getAcceptAckCode());
		logger.debug("response type id: " + response.getTypeId());

		final JXPathContext context = JXPathContext.newContext(response);
		final Iterator<MCCIMT000300UV01Acknowledgement> ackmntList = context
				.iterate("/acknowledgement");

		while (ackmntList.hasNext()) {
			final MCCIMT000300UV01Acknowledgement ackmnt = ackmntList.next();

			if (ackmnt.getTypeCode().getCode().equals("AA")) {
				final StringBuffer queryMsg = new StringBuffer(
						"Query Success! ");
				final Map<String, String> idMap = new HashMap<String, String>();

				final Iterator<PRPAMT201307UV02PatientIdentifier> pidList = context
						.iterate("/controlActProcess/queryByParameter/value/parameterList/patientIdentifier");
				while (pidList.hasNext()) {
					final PRPAMT201307UV02PatientIdentifier pid = pidList
							.next();
					final List<II> ptIdList = pid.getValue();

					for (final II ptId : ptIdList) {
						queryMsg.append(" Given PID: " + ptId.getExtension());
						queryMsg.append(" Given UID: " + ptId.getRoot());
						queryMsg.append("\t");
					}

				}

				final Iterator<II> ptIdList = context
						.iterate("/controlActProcess/subject[1]/registrationEvent/subject1[typeCode='SBJ']/patient[classCode='PAT']/id");

				while (ptIdList.hasNext()) {
					final II pId = ptIdList.next();
					idMap.put(pId.getRoot(), pId.getExtension());
					// System.out.println("Requested UID:  " + pId.getRoot());
					// System.out.println("Requested PID:  " +
					// pId.getExtension());
					// queryMsg.append(" Requested PID: " + pId.getExtension());
					// queryMsg.append(" Requested UID: " + pId.getRoot());
					// queryMsg.append("\t");
				}

				pixMgrBean.setQueryMessage(queryMsg.toString());
				pixMgrBean.setQueryIdMap(idMap);
				break;
			} else if (ackmnt.getTypeCode().getCode().equals("AE")) {

				final List<MCCIMT000300UV01AcknowledgementDetail> ackmntDetList = ackmnt
						.getAcknowledgementDetail();
				for (final MCCIMT000300UV01AcknowledgementDetail ackDet : ackmntDetList) {
					logger.error("Query Failure! "
							+ ackDet.getText().getContent());
					pixMgrBean.setQueryMessage("Query Failure! "
							+ ackDet.getText().getContent());
					pixMgrBean.setQueryIdMap(null);
					break;
				}

			} else {
				pixMgrBean.setQueryMessage("Query Failure! ");
				pixMgrBean.setQueryIdMap(null);
			}
		}
		return pixMgrBean;
	}

	/**
	 * Checks if is success.
	 *
	 * @param response
	 *            the response
	 * @return true, if is success
	 */
	public boolean isSuccess(MCCIIN000002UV01 response) {
		return response.getAcknowledgement().stream()
				.map(a -> a.getTypeCode().getCode())
				.filter(c -> "CA".equals(c)).count() > 0;
	}
}
