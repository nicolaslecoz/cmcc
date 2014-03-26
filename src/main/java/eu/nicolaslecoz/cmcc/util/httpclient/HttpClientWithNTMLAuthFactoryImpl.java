package eu.nicolaslecoz.cmcc.util.httpclient;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.springframework.beans.factory.annotation.Required;

/**
 * HttpClient avec une connection avec un proxy avec un protocol NTML (Microsoft)
 * http://davenport.sourceforge.net/ntlm.html
 * 
 * @author Nicolas Le Coz
 * @since 5 avril 2010
 */
public class HttpClientWithNTMLAuthFactoryImpl implements HttpClientFactory {
	private int httpClientProxyPort;
	private String httpClientProxyHost;
	private String httpClientProxyUser;
	private String httpClientProxyPassword;
	private String httpClientProxyDomain;
	
	@Override
	public HttpClient buildAndConfigure(int httpClientTimeout) {
		HttpClient httpClient = new HttpClient();

		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(
				httpClientTimeout);

		httpClient.getHostConfiguration().setProxy(this.httpClientProxyHost, this.httpClientProxyPort);
		
		httpClient.getState().setProxyCredentials(
				new AuthScope(this.httpClientProxyHost, this
						.getHttpClientProxyPort()),
				new NTCredentials(this.httpClientProxyUser,
						this.httpClientProxyPassword, this.httpClientProxyHost, this.httpClientProxyDomain));
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

	public String getHttpClientProxyUser() {
		return httpClientProxyUser;
	}

	@Required
	public void setHttpClientProxyUser(String httpClientProxyUser) {
		this.httpClientProxyUser = httpClientProxyUser;
	}

	public String getHttpClientProxyPassword() {
		return httpClientProxyPassword;
	}

	@Required
	public void setHttpClientProxyPassword(String httpClientProxyPassword) {
		this.httpClientProxyPassword = httpClientProxyPassword;
	}

	public String getHttpClientProxyDomain() {
		return httpClientProxyDomain;
	}

	@Required
	public void setHttpClientProxyDomain(String httpClientProxyDomain) {
		this.httpClientProxyDomain = httpClientProxyDomain;
	}
}
