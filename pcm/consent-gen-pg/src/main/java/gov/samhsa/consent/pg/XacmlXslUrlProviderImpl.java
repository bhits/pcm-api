package gov.samhsa.consent.pg;

import gov.samhsa.consent.XslResource;

public class XacmlXslUrlProviderImpl extends
		gov.samhsa.consent.XacmlXslUrlProviderImpl {

	@Override
	public String getUrl(XslResource xslResource) {
		String packageName = null;
		String fileName = xslResource.getFileName();
		if (XslResource.XACMLXSLNAME.equals(xslResource)) {
			packageName = this.getClass().getPackage().getName();
		} else {
			packageName = this.getClass().getSuperclass().getPackage()
					.getName();
		}
		return getUrl(packageName, fileName);
	}
}
