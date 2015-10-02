package gov.samhsa.acs.polrep.client;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class NVPBuilder {

	private final List<NameValuePair> nvps = new LinkedList<NameValuePair>();

	public NVPBuilder and(String name, String value) {
		nvps.add(new BasicNameValuePair(name, value));
		return this;
	}

	public List<NameValuePair> build() {
		return this.nvps;
	}

	public static List<NameValuePair> identity() {
		return new NVPBuilder().build();
	}

	public static NVPBuilder withParam(String name, String value) {
		final NVPBuilder builder = new NVPBuilder();
		return builder.and(name, value);
	}

}
