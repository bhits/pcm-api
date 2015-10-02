package gov.samhsa.acs.common.cxf;

import java.util.function.Supplier;

import org.apache.cxf.endpoint.Client;

public abstract class AbstractCXFLoggingConfigurerClient {

	private boolean enableLoggingInterceptors;

	public boolean isEnableLoggingInterceptors() {
		return this.enableLoggingInterceptors;
	}

	public void setEnableLoggingInterceptors(boolean enableLoggingInterceptors) {
		this.enableLoggingInterceptors = enableLoggingInterceptors;
	}

	protected <T extends Client> T configurePort(Supplier<T> clientSupplier) {
		final T client = clientSupplier.get();
		CXFLoggingConfigurer.configureInterceptors(client, CXFLoggingConfigurer
				.serviceNameWithInvokingInstance(client, this),
				isEnableLoggingInterceptors());
		return client;
	}
}
