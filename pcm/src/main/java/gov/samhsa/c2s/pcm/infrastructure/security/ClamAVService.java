package gov.samhsa.c2s.pcm.infrastructure.security;

import fi.solita.clamav.ClamAVClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The Class ClamAVService.
 */
@Service
public class ClamAVService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Host where 'clamd' process is running.
     */
    @Value("${c2s.pcm.config.clamd.host}")
    private String clamdHost;

    /**
     * Port on which 'clamd' process is listening.
     */
    @Value("${c2s.pcm.config.clamd.port}")
    private int clamdPort;

    /**
     * Connection time out to connect 'clamd' process.
     */
    @Value("${c2s.pcm.config.clamd.connTimeOut}")
    private int connTimeOut;
    /**
     * The scanner.
     */
    private ClamAVClient scanner;

    /**
     * Gets the clamd host.
     *
     * @return the clamd host
     */
    public String getClamdHost() {
        return this.clamdHost;
    }

    /**
     * Sets the clamd host.
     *
     * @param clamdHost the new clamd host
     */
    public void setClamdHost(String clamdHost) {
        this.clamdHost = clamdHost;
    }

    /**
     * Gets the clamd port.
     *
     * @return the clamd port
     */
    public int getClamdPort() {
        return this.clamdPort;
    }

    /**
     * Sets the clamd port.
     *
     * @param clamdPort the new clamd port
     */
    public void setClamdPort(int clamdPort) {
        this.clamdPort = clamdPort;
    }

    /**
     * Gets the conn time out.
     *
     * @return the conn time out
     */
    public int getConnTimeOut() {
        return this.connTimeOut;
    }

    /**
     * Sets the conn time out.
     *
     * @param connTimeOut the new conn time out
     */
    public void setConnTimeOut(int connTimeOut) {
        this.connTimeOut = connTimeOut;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @PostConstruct
    public void afterPropertiesSet() {
        this.scanner = new ClamAVClient(clamdHost, clamdPort, connTimeOut);
    }

    /**
     * Gets the clam av scanner client.
     *
     * @return the clam av scanner client
     */
    public ClamAVClient getClamAVScanner() {
        return scanner;
    }

    /**
     * Method scans files to check whether file is virus infected.
     *
     * @param destFilePath file path
     * @return true, if successful
     * @throws IOException thrown if fileInputStream encounters an IOException
     * @throws ClamAVClientNotAvailableException thrown if the ClamAV client cannot connect successfully to the remote ClamAV Server scanner,
     * or if the remote server aborts the connection
     */
    public boolean fileScanner(String destFilePath) throws IOException, ClamAVClientNotAvailableException {
        return fileScanner(new FileInputStream(destFilePath));
    }

    /**
     * Method scans files to check whether file is virus infected.
     *
     * @param fileInputStream the file input stream
     * @return true, if successful
     * @throws IOException thrown if fileInputStream encounters an IOException
     * @throws ClamAVClientNotAvailableException thrown if the ClamAV client cannot connect successfully to the remote ClamAV Server scanner,
     * or if the remote server aborts the connection
     */
    public boolean fileScanner(InputStream fileInputStream) throws IOException, ClamAVClientNotAvailableException {
        boolean resScan;
        afterPropertiesSet();

        if (fileInputStream != null) {
            byte[] scannerReply;

            try {
                scannerReply = scanner.scan(fileInputStream);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new ClamAVClientNotAvailableException("ClamAV service not available or server aborted connection.");
            } finally {
                //Close the file input stream
                fileInputStream.close();
            }

            resScan = ClamAVClient.isCleanReply(scannerReply);
        } else {
            throw new IOException("fileInputStream passed to ClamAVServer fileScanner was null.");
        }

        return resScan;
    }
}
