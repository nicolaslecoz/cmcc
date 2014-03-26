package eu.nicolaslecoz.cmcc.util.httpclient;

import org.apache.commons.httpclient.HttpClient;

/**
 * HttpClient avec une connection directe à internet
 * 
 * @author Nicolas Le Coz
 * @since 5 avril 2010
 */
public class HttpClientSimpleFactoryImpl implements HttpClientFactory {

	@Override
	public HttpClient buildAndConfigure(int httpClientTimeout) {
		HttpClient httpClient = new HttpClient();

		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(
				httpClientTimeout);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(
				httpClientTimeout);
		return httpClient;
	}
}
