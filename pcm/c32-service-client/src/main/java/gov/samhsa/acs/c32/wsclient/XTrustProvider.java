/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.acs.c32.wsclient;

import java.security.AccessController;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivilegedAction;
import java.security.Security;
import java.security.cert.X509Certificate;
  
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509TrustManager;

/**
 * The Class XTrustProvider.
 */
public final class XTrustProvider extends java.security.Provider {
	
	/** The Constant NAME. */
	private final static String NAME = "XTrustJSSE";
	
	/** The Constant INFO. */
	private final static String INFO = "XTrust JSSE Provider (implements trust factory with truststore validation disabled)";
	
	/** The Constant VERSION. */
	private final static double VERSION = 1.0D;
	
	/**
	 * Instantiates a new x trust provider.
	 */
	public XTrustProvider() {
		super(NAME, VERSION, INFO);
		
		AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				put("TrustManagerFactory." + TrustManagerFactoryImpl.getAlgorithm(), TrustManagerFactoryImpl.class.getName());
				return null;
			}
		});
	}
	
	/**
	 * Install.
	 */
	public static void install() {
		if(Security.getProvider(NAME) == null) {
			Security.insertProviderAt(new XTrustProvider(), 2);
			Security.setProperty("ssl.TrustManagerFactory.algorithm", TrustManagerFactoryImpl.getAlgorithm());
		}
	}
	
	/**
	 * The Class TrustManagerFactoryImpl.
	 */
	public final static class TrustManagerFactoryImpl extends TrustManagerFactorySpi {
		
		/**
		 * Instantiates a new trust manager factory impl.
		 */
		public TrustManagerFactoryImpl() { }
		
		/**
		 * Gets the algorithm.
		 *
		 * @return the algorithm
		 */
		public static String getAlgorithm() { return "XTrust509"; }
		
		/* (non-Javadoc)
		 * @see javax.net.ssl.TrustManagerFactorySpi#engineInit(java.security.KeyStore)
		 */
		protected void engineInit(KeyStore keystore) throws KeyStoreException { }
		
		/* (non-Javadoc)
		 * @see javax.net.ssl.TrustManagerFactorySpi#engineInit(javax.net.ssl.ManagerFactoryParameters)
		 */
		protected void engineInit(ManagerFactoryParameters mgrparams) throws InvalidAlgorithmParameterException {
			throw new InvalidAlgorithmParameterException( XTrustProvider.NAME + " does not use ManagerFactoryParameters");
		}
		
		/* (non-Javadoc)
		 * @see javax.net.ssl.TrustManagerFactorySpi#engineGetTrustManagers()
		 */
		protected TrustManager[] engineGetTrustManagers() {
			return new TrustManager[] {
				new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() { return null; }
					public void checkClientTrusted(X509Certificate[] certs, String authType) { }
					public void checkServerTrusted(X509Certificate[] certs, String authType) { }
				}
			};
		}
	}
}
