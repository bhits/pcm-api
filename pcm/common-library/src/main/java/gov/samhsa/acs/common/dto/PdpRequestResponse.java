package gov.samhsa.acs.common.dto;

public class PdpRequestResponse {
	private XacmlRequest xacmlRequest;
	private XacmlResponse xacmlResponse;

	public XacmlRequest getXacmlRequest() {
		return xacmlRequest;
	}

	public void setXacmlRequest(XacmlRequest xacmlRequest) {
		this.xacmlRequest = xacmlRequest;
	}

	public XacmlResponse getXacmlResponse() {
		return xacmlResponse;
	}

	public void setXacmlResponse(XacmlResponse xacmlResponse) {
		this.xacmlResponse = xacmlResponse;
	}
}
