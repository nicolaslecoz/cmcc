package eu.nicolaslecoz.cmcc.util.httpclient;

import org.apache.commons.httpclient.HttpClient;

/**
 * Service permettant de proposer différentes stratégies
 * de construction d'un client HTTP (suivant les environnements)
 * 
 * @author Nicolas Le Coz
 * @since 5 avril 2010
 */
public interface HttpClientFactory {
	HttpClient buildAndConfigure(int httpClientTimeout);
}
