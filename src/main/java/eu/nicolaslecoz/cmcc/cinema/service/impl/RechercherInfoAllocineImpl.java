package eu.nicolaslecoz.cmcc.cinema.service.impl;

import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;


import eu.nicolaslecoz.cmcc.cinema.domain.ApercuFilm;
import eu.nicolaslecoz.cmcc.cinema.service.RechercherInfoAllocine;


/**
 * 
 * @author Nicolas Le Coz
 * @since 8th septembre 2010
 */
public class RechercherInfoAllocineImpl implements RechercherInfoAllocine {
	static private Logger logger = LoggerFactory.getLogger(RechercherInfoAllocineImpl.class);
	
	private String allocineRechercheUrl;
	private int allocineHttpClientConnectionTimeout;
	
	private String computeTitreOriginal(Element element) {
		String contentAllocine = element.parent().text();
		
		if (contentAllocine.indexOf("(") == -1 && contentAllocine.indexOf(")") == -1) {
			return null;
		}
		return contentAllocine.substring(
				contentAllocine.indexOf("(") + 1,
				contentAllocine.lastIndexOf(")"));
	}
	
	@Override
	public List<ApercuFilm> rechercher(String recherche) {
		List<ApercuFilm> result = new ArrayList<ApercuFilm>();
		
		Document doc = null;
		
		try {
			doc = Jsoup.parse(new URL(MessageFormat.format(this.allocineRechercheUrl, URLEncoder.encode(recherche, "UTF-8"))), 
				allocineHttpClientConnectionTimeout);
		}
		catch(Exception e) {
			logger.warn("Erreur Recherche de film sur Allocine :" + recherche, e);
		}
		
		// elements contenant le texte
		Elements txtElts = doc.select("div.colcontent").select("div > a[href^=/film/fichefilm_gen_cfilm=]");
				
		// elements contenant l'affiche du film
		Elements imgElts = doc.select("div.colcontent").select("a[href^=/film/fichefilm_gen_cfilm=] > img");		
		
		if (txtElts == null || imgElts == null) {
			return result;
		}
		for (int i = 0; i < txtElts.size(); i++) {
			ApercuFilm film = new ApercuFilm();
			
			film.setTitre(txtElts.get(i).select("b").text());
			
			film.setTitreOriginal(computeTitreOriginal(txtElts.get(i))); // TODO: KO
			
			film.setUrlAllocineFicheFilm(txtElts.get(i).attr("href"));
			
			// TODO: test avec un acteur un nom accentué
			String[] elts = txtElts.get(i).parent().select("span").html().split("<br />");
			
			for (String elt : elts) {
				String current = elt.trim();
				
				if (StringUtils.isNotEmpty(current)) {
					if (StringUtils.isNumeric(current)) {
						film.setAnnee(Integer.parseInt(current));
					}
					else if (StringUtils.startsWithIgnoreCase(current, "de")) {
						String[] realisateurs = current.substring(3).split(",");
						
						for (String realisateur : realisateurs) {
							film.addRealisateur(realisateur.trim());
						}
					}
					else if (StringUtils.startsWithIgnoreCase(current, "avec")) {
						String[] acteurs = current.substring(5).split(",");
						
						for (String acteur : acteurs) {
							film.addActeur(acteur.trim());
						}						
					}
				}
			}
			Element imgElt = imgElts.get(i);
			
			if (imgElt != null) {
				film.setUrlAllocineAfficheMini(imgElt.attr("src"));
			}			
			result.add(film);
		}
		
		return result;
	}

	public String getAllocineRechercheUrl() {
		return allocineRechercheUrl;
	}

	@Required
	public void setAllocineRechercheUrl(String allocineRechercheUrl) {
		this.allocineRechercheUrl = allocineRechercheUrl;
	}

	public int getAllocineHttpClientConnectionTimeout() {
		return allocineHttpClientConnectionTimeout;
	}
	
	@Required
	public void setAllocineHttpClientConnectionTimeout(
			int allocineHttpClientConnectionTimeout) {
		this.allocineHttpClientConnectionTimeout = allocineHttpClientConnectionTimeout;
	}
}
