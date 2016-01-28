package gov.samhsa.mhc.pcm.infrastructure.security;

import net.taldius.clamav.ClamAVScanner;
import net.taldius.clamav.ClamAVScannerFactory;
import net.taldius.clamav.ScannerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
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
    @Value("${mhc.pcm.config.clamd.host}")
    private String clamdHost;

    /**
     * Port on which 'clamd' process is listening.
     */
    @Value("${mhc.pcm.config.clamd.port}")
    private int clamdPort;

    /**
     * Connection time out to connect 'clamd' process.
     */
    @Value("${mhc.pcm.config.clamd.connTimeOut}")
    private int connTimeOut;
    /**
     * The scanner.
     */
    private ClamAVScanner scanner;

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
    public void afterPropertiesSet() throws Exception {
        ClamAVScannerFactory.setClamdHost(clamdHost);

        ClamAVScannerFactory.setClamdPort(clamdPort);

        if (connTimeOut > 0) {
            ClamAVScannerFactory.setConnectionTimeout(connTimeOut);
        }
        this.scanner = ClamAVScannerFactory.getScanner();
    }

    /**
     * Gets the clam av scanner.
     *
     * @return the clam av scanner
     */
    public ClamAVScanner getClamAVScanner() {
        return scanner;
    }

    /**
     * Method scans files to check whether file is virus infected.
     *
     * @param destFilePath file path
     * @return true, if successful
     * @throws Exception the exception
     */
    public boolean fileScanner(String destFilePath) throws Exception {

        return fileScanner(new FileInputStream(destFilePath));
    }

    /**
     * Method scans files to check whether file is virus infected.
     *
     * @param fileInputStream the file input stream
     * @return true, if successful
     * @throws Exception the exception
     */
    public boolean fileScanner(InputStream fileInputStream) throws ClamAVClientNotAvailableException, Exception {

        boolean resScan = false;
        afterPropertiesSet();

        if (fileInputStream != null) {
            try {
                resScan = scanner.performScan(fileInputStream);
            } catch (ScannerException e) {
                logger.error(e.getMessage(), e);
                throw new ClamAVClientNotAvailableException(
                        "ClamAV service not available.");
            }

        } else {

            throw new Exception();
        }
        return resScan;
    }
}
