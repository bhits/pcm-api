package gov.samhsa.c2s.pcm.infrastructure;

import gov.samhsa.c2s.pcm.config.PcmProperties;
import gov.samhsa.c2s.pcm.infrastructure.exception.SqlScriptFileException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ClasspathSqlScriptProvider implements SqlScriptProvider {

    /**
     * The Constant UTF_8.
     */
    private static final String UTF_8 = "UTF-8";

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PcmProperties pcmProperties;

    public ClasspathSqlScriptProvider() throws SqlScriptFileException {
    }

    @Override
    public String getSqlScript() throws SqlScriptFileException {
        try {
            final Resource sqlResource = new ClassPathResource(pcmProperties.getActivity().getSql().getPath());
            final String sql = IOUtils.toString(sqlResource.getInputStream(), UTF_8);
            logger.debug(sqlResource.getFilename() + " SQL Script:");
            logger.debug(sql);
            return sql;
        } catch (final IOException e) {
            logger.error(e.getMessage(), e);
            throw new SqlScriptFileException("SQL script file cannot be found:" + e);
        }
    }
}
