package gov.samhsa.consent2share.commonunit.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

public class SpringBasedResourceFileReader {
	
	public static String getStringFromResourceFile(String resourceFileUri) {
		StringWriter writer = new StringWriter();
		ClassPathResource cpr = new ClassPathResource(resourceFileUri);
		try {
			InputStream inputStream = cpr.getInputStream();
			IOUtils.copy(inputStream, writer);
		} catch (IOException e) {
		}

		String result = writer.toString();
		try {
			writer.close();
		} catch (IOException e) {
		}

		return result;
	}

}
