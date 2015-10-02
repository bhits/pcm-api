package gov.samhsa.consent;

import gov.samhsa.acs.common.tool.ResourceUrlProvider;

public interface XacmlXslUrlProvider extends ResourceUrlProvider {
	public abstract String getUrl(XslResource xslResource);
}
