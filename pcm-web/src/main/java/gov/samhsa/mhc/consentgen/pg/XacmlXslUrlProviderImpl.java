package gov.samhsa.mhc.consentgen.pg;


import gov.samhsa.mhc.consentgen.XslResource;

public class XacmlXslUrlProviderImpl extends
        gov.samhsa.mhc.consentgen.XacmlXslUrlProviderImpl {

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
