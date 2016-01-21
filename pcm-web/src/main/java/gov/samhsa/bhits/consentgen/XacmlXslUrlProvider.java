package gov.samhsa.bhits.consentgen;


import gov.samhsa.bhits.common.url.ResourceUrlProvider;

public interface XacmlXslUrlProvider extends ResourceUrlProvider {
    public abstract String getUrl(XslResource xslResource);
}
