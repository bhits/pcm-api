package gov.samhsa.mhc.pcm.infrastructure;


import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import gov.samhsa.mhc.pcm.infrastructure.exception.SqlScriptFileException;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ClasspathSqlScriptProvider implements SqlScriptProvider {

	/** The Constant UTF_8. */
	private static final String UTF_8 = "UTF-8";

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The sql script file location. */
	@Value("${mhc.pcm.config.activity.sql.path}")
	private String sqlScriptFileLocation;

	public ClasspathSqlScriptProvider() throws SqlScriptFileException {
		super();
	}

	@Override
	public String getSqlScript(){
		try {
			if (!new ClassPathResource(this.sqlScriptFileLocation).exists()) {
				final String errorMessage = createSqlNotFountExceptionMessage();
				logger.error(errorMessage);
				throw new SqlScriptFileException(errorMessage);
			}else{
				final Resource sqlResource = new ClassPathResource(this.sqlScriptFileLocation);
				final String sql = IOUtils.toString(sqlResource.getInputStream(),UTF_8);
				logger.debug("Activity History SQL Script:");
				logger.debug(sql);
				return sql;
			}
		} catch (final IOException e) {
			logger.error(e.getMessage(), e);
			throw new SqlScriptFileException(e);
		}
	}

	private String createSqlNotFountExceptionMessage() {
		return new StringBuilder().append("SQL script file for ")
				.append("Activity History")
				.append(" cannot be found!").toString();
	}

}
