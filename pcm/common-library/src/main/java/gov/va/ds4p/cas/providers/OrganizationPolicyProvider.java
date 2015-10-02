package gov.va.ds4p.cas.providers;

import gov.va.ds4p.policy.reference.OrganizationPolicy;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class OrganizationPolicyProvider {
	public OrganizationPolicy createOrganizationPolicyObjectFromXML(
			String ruleXML) {
		OrganizationPolicy obj = null;
		try {
			JAXBContext context = JAXBContext
					.newInstance(new Class[] { OrganizationPolicy.class });
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader sr = new StringReader(ruleXML);

			Object o = unmarshaller.unmarshal(sr);
			obj = (OrganizationPolicy) o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public String createOrganizationPolicyXMLFromObject(OrganizationPolicy obj) {
		String res = "";
		try {
			JAXBContext context = JAXBContext
					.newInstance(new Class[] { OrganizationPolicy.class });
			Marshaller marshaller = context.createMarshaller();
			StringWriter sw = new StringWriter();
			marshaller.setProperty("jaxb.formatted.output",
					Boolean.valueOf(true));

			marshaller.marshal(obj, sw);

			res = sw.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return res;
	}
}
