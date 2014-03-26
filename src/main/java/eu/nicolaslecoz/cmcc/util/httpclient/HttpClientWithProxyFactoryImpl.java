package eu.nicolaslecoz.cmcc.util.httpclient;

import org.apache.commons.httpclient.HttpClient;
import org.springframework.beans.factory.annotation.Required;

/**
 * HttpClient avec une connection via un proxy
 * 
 * @author Nicolas Le Coz
 * @since 5 avril 2010
 */
public class HttpClientWithProxyFactoryImpl implements HttpClientFactory {
	private int httpClientProxyPort;
	private String httpClientProxyHost;
	
	@Override
	public HttpClient buildAndConfigure(int httpClientTimeout) {
		HttpClient httpClient = new HttpClient();

		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(
				httpClientTimeout);

		httpClient.getHostConfiguration().setProxy(this.httpClientProxyHost, this.httpClientProxyPort);

		return httpClient;
	}

	public int getHttpClientProxyPort() {
		return httpClientProxyPort;
	}

	@Required
	public void setHttpClientProxyPort(int httpClientProxyPort) {
		this.httpClientProxyPort = httpClientProxyPort;
	}

	public String getHttpClientProxyHost() {
		return httpClientProxyHost;
	}

	@Required
	public void setHttpClientProxyHost(String httpClientProxyHost) {
		this.httpClientProxyHost = httpClientProxyHost;
	}
}
