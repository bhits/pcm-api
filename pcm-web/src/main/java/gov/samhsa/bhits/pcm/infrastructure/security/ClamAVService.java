package gov.samhsa.bhits.pcm.infrastructure.security;

import net.taldius.clamav.ClamAVScanner;
import net.taldius.clamav.ClamAVScannerFactory;
import net.taldius.clamav.ScannerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * The Class ClamAVService.
 */
public class ClamAVService implements InitializingBean {

	/** Host where 'clamd' process is running. */
	private String clamdHost;

	/** Port on which 'clamd' process is listening. */
	private String clamdPort;

	/** Connection time out to connect 'clamd' process. */
	private String connTimeOut;

	/** The scanner. */
	private ClamAVScanner scanner;
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Instantiates a new clam av service.
	 *
	 * @param clamdHost
	 *            the clamd host
	 * @param clamdPort
	 *            the clamd port
	 * @param connTimeOut
	 *            the conn time out
	 */
	public ClamAVService(String clamdHost, String clamdPort, String connTimeOut) {
		super();
		this.clamdHost = clamdHost;
		this.clamdPort = clamdPort;
		this.connTimeOut = connTimeOut;
	}

	/**
	 * Sets the clamd host.
	 *
	 * @param clamdHost
	 *            the new clamd host
	 */
	public void setClamdHost(String clamdHost) {
		this.clamdHost = clamdHost;
	}

	/**
	 * Gets the clamd host.
	 *
	 * @return the clamd host
	 */
	public String getClamdHost() {
		return this.clamdHost;
	}

	/**
	 * Sets the clamd port.
	 *
	 * @param clamdPort
	 *            the new clamd port
	 */
	public void setClamdPort(String clamdPort) {
		this.clamdPort = clamdPort;
	}

	/**
	 * Gets the clamd port.
	 *
	 * @return the clamd port
	 */
	public String getClamdPort() {
		return this.clamdPort;
	}

	/**
	 * Sets the conn time out.
	 *
	 * @param connTimeOut
	 *            the new conn time out
	 */
	public void setConnTimeOut(String connTimeOut) {
		this.connTimeOut = connTimeOut;
	}

	/**
	 * Gets the conn time out.
	 *
	 * @return the conn time out
	 */
	public String getConnTimeOut() {
		return this.connTimeOut;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		initScanner();
	}

	/**
	 * Method to initialize clamAV scanner.
	 */
	public void initScanner() {

		ClamAVScannerFactory.setClamdHost(clamdHost);

		ClamAVScannerFactory.setClamdPort(Integer.parseInt(clamdPort));

		int connectionTimeOut = Integer.parseInt(connTimeOut);

		if (connectionTimeOut > 0) {
			ClamAVScannerFactory.setConnectionTimeout(connectionTimeOut);
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
	 * @param destFilePath
	 *            file path
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	public boolean fileScanner(String destFilePath) throws Exception {

		return fileScanner(new FileInputStream(destFilePath));
	}

	/**
	 * Method scans files to check whether file is virus infected.
	 *
	 * @param fileInputStream
	 *            the file input stream
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	public boolean fileScanner(InputStream fileInputStream) throws ClamAVClientNotAvailableException, Exception {

		boolean resScan = false;
		initScanner();

		if (fileInputStream != null) {
			try {
				resScan = scanner.performScan(fileInputStream);
			} catch (ScannerException e) {
				logger.error(e.getMessage(),e);
				throw new ClamAVClientNotAvailableException(
						"ClamAV service not available.");
			}

		} else {

			throw new Exception();
		}
		return resScan;
	}
}
