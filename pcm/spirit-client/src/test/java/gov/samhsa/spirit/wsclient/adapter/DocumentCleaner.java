package gov.samhsa.spirit.wsclient.adapter;

import gov.samhsa.spirit.wsclient.dto.EhrPatientClientListDto;
import gov.samhsa.spirit.wsclient.exception.SpiritAdapterException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;

import com.spirit.ehr.ws.client.generated.EhrException_Exception;
import com.spirit.ehr.ws.client.generated.EhrXdsQRsp;

public class DocumentCleaner {

	public static void main(String[] args) {
		String id = "C2S.PG-DEV.RmETWp:&amp;2.16.840.1.113883.3.704.100.200.1.1.3.1&amp;ISO:1427467752:C2S Health:XM2UoY";
		String[] tokens = id.split("&amp;");
		String nId = StringUtils.join(tokens, "&");
		System.out.println(nId);
		id.replaceAll("&amp;", "&");
		System.out.println(id);
		/*
		 * String[] tokens = id.split(":"); int tokenCount = tokens.length;
		 * //assert the no of elements as 5 for(String s : tokens){
		 * System.out.println(s); } String consentTo = tokens[tokenCount-3];
		 * String consentFrom = tokens[tokenCount-2];
		 * tokens[tokenCount-3]=consentFrom; tokens[tokenCount-2] =
		 * "C2S Health"; String joined = StringUtils.join(tokens, ":");
		 * System.out.println("joined" + joined); for(String s : tokens){
		 * System.out.println(s); }
		 */
	}

	public static void main1(String[] args) throws URISyntaxException,
			IOException, SpiritAdapterException, EhrException_Exception {

		ClassLoader classloader = Thread.currentThread()
				.getContextClassLoader();

		URL resource = classloader.getResource("spiritconfig.properties");
		File file = new File(resource.toURI());
		FileInputStream fis = new FileInputStream(file);
		Properties props = new Properties();
		props.load(fis);

		// reading proeprty
		String endpointAddress = props
				.getProperty("spirit.client.endpointAddress");
		String org = props.getProperty("spirit.client.org");
		String pwd = props.getProperty("spirit.client.pwd");
		String rol = props.getProperty("spirit.client.rol");
		String user = props.getProperty("spirit.client.user");
		final String domain = props.getProperty("spirit.client.domainId");
		final String c2sDomain = props
				.getProperty("spirit.client.c2s.domainId");
		final String c2sDomainType = props
				.getProperty("spirit.client.c2s.domainType");
		final String c2sEnvType = props.getProperty("spirit.client.pid.prefix");

		// SpiritEhrWsClientRqRspInterface webService =
		// InterfaceFactory.createEhrWsRqRspInterface(endpointAddress);
		// SpiritWebServiceClient client = new
		// SpiritWebServiceClient(endpointAddress);
		SpiritAdapter adapter = new SpiritAdapterImpl(endpointAddress, org,
				pwd, rol, user, domain, c2sDomain, c2sDomainType, c2sEnvType);
		String stateid = adapter.login();
		Scanner s = new Scanner(System.in);
		System.out.println("Please enter Local Patient Identifier");
		String pid = s.nextLine();
		s.close();
		EhrPatientClientListDto patientResponse = adapter
				.queryPatientsWithPids(pid, stateid);
		boolean allDeleted = false;
		String deleteStatus = null;
		int deleteCounter = 0;
		while (!allDeleted) {
			EhrXdsQRsp contentResponse = adapter.queryPatientContent(
					patientResponse.getEhrPatientClientListDto().get(0),
					stateid);
			allDeleted = contentResponse.getResponseData().getDocuments() == null
					|| contentResponse.getResponseData().getDocuments().size() == 0;
			if (allDeleted)
				break;
			deleteStatus = adapter.deleteDocuments(null, contentResponse
					.getResponseData().getDocuments(), patientResponse
					.getEhrPatientClientListDto().get(0), stateid);
			deleteCounter += contentResponse.getResponseData().getDocuments()
					.size();
		}

		if (deleteCounter == 0 || deleteStatus.equals("Success"))
			System.out
					.println("All the documents under patient "
							+ pid
							+ " are now removed. The total number of deleted documents: "
							+ deleteCounter + ".");

		// logout
		adapter.logout(stateid);
	}

}
