package gov.samhsa.mhc.consentgen;


import gov.samhsa.mhc.common.url.ResourceUrlProvider;

public interface XacmlXslUrlProvider extends ResourceUrlProvider {
    public abstract String getUrl(XslResource xslResource);
}
