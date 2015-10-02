package gov.samhsa.consent2share.commonunit.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

public class ResourceFileReader {
	
	public static String getStringFromResourceFile(String resourceFileUri) {
		StringWriter writer = new StringWriter();
		InputStream inputStream = Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(resourceFileUri);
		try {
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
