package gov.samhsa.mhc.consentgen;


public class XacmlXslUrlProviderImpl implements XacmlXslUrlProvider {

	@Override
	public String getUrl(XslResource xslResource) {
		final String packageName = this.getClass().getPackage().getName();
		final String fileName = xslResource.getFileName();
		return getUrl(packageName, fileName);
	}

}
