package eu.nicolaslecoz.cmcc.cinema.service.impl;

import java.net.URL;
import java.text.MessageFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;


import eu.nicolaslecoz.cmcc.cinema.domain.ApercuFilm;
import eu.nicolaslecoz.cmcc.cinema.domain.Film;
import eu.nicolaslecoz.cmcc.cinema.domain.GenreType;
import eu.nicolaslecoz.cmcc.cinema.domain.Personne;
import eu.nicolaslecoz.cmcc.cinema.domain.RoleType;
import eu.nicolaslecoz.cmcc.cinema.service.RecupererInfoAllocine;

/**
 * 
 * @author Nicolas LE COZ <lecoz.nicolas@gmail.com>
 * @since 12 septembre 2010
 */
public class RecuperInfoAllocineImpl implements RecupererInfoAllocine {
	static private Logger logger = LoggerFactory.getLogger(RecuperInfoAllocineImpl.class);

	static private final String PATTERN_URL_FILM = "http://www.allocine.fr/film/fichefilm_gen_cfilm={0}.html";
	static private final String PATTERN_URL_AFFICHE_FILM = "http://www.allocine.fr/film/fichefilm-{0}/affiches/";
	static private final String PATTERN_URL_CRITIQUE_FILM = "http://www.allocine.fr/film/revuedepresse_gen_cfilm={0}.html";
	static private final String PATTERN_URL_CASTING_FILM = "http://www.allocine.fr/film/casting_gen_cfilm={0}.html";

	private int allocineHttpClientConnectionTimeout;

	private GenreType computeGenreTypeByName(String name) {
		GenreType result = null;
		
		
		if (name == null) {
			return result;
		}		
		try {
			result = GenreType.valueOf(
					name.trim().replaceAll(" ", "_").replaceAll("é", "E").toUpperCase()
				);
		}
		catch (IllegalArgumentException e) {
		}
		return result;
	}
	
	private String computeUrlFilm(String allocineId) {
		return MessageFormat.format(PATTERN_URL_FILM, allocineId);
	}	
	
	private String computeUrlAfficheFilm(String allocineId) {
		return MessageFormat.format(PATTERN_URL_AFFICHE_FILM, allocineId);
	}

	private String computeUrlCritiqueFilm(String allocineId) {
		return MessageFormat.format(PATTERN_URL_CRITIQUE_FILM, allocineId);
	}
	
	private String computeUrlCastingFilm(String allocineId) {
		return MessageFormat.format(PATTERN_URL_CASTING_FILM, allocineId);
	}
	
	private void recupererInfoFromFicheFilm(Film result, String allocineId) {
		Document doc = null;
		
		try {
			doc = Jsoup.parse(new URL(computeUrlFilm(allocineId)), allocineHttpClientConnectionTimeout);
		}
		catch(Exception e) {
			logger.warn("Erreur Fiche film sur Allocine :" + allocineId, e);
		}
		result.setSynopsis(doc.select("p[itemprop^=description").text()); 
		
		Elements genreElts = doc.select("span[itemprop^=genre");
		
		for (Element elt : genreElts) {
			GenreType t = computeGenreTypeByName(elt.text());
			
			if (t != null && !result.getGenres().contains(t)) {
				result.addGenreType(t);
			}
		}
	}
	
	private String computeAllocineIdFromFicheActeurUrl(String url) {
		// de la forme :
		// /personne/fichepersonne_gen_cpersonne=8407.html
		
		int lastIndexOfEqual = url.lastIndexOf("=");
		int lastIndexOfDotHtml = url.lastIndexOf(".html");
		if (lastIndexOfEqual == -1 || lastIndexOfDotHtml == -1) {
			return "";
		}
		return url.substring(lastIndexOfEqual + 1, lastIndexOfDotHtml);
	}
	
	
	private void recupererInfoFromCastingFilm(Film film, String allocineId) {
		Document doc = null;
		
		try {
			doc = Jsoup.parse(new URL(computeUrlCastingFilm(allocineId)), allocineHttpClientConnectionTimeout);
		}
		catch(Exception e) {
			logger.warn("Erreur Casting film sur Allocine :" + allocineId, e);
		}
		
		Elements directorsElt = doc.select("li[itemprop=director]");
		
		for (Element directorElt : directorsElt) {
			Personne realisateur = new Personne();
			
			recupererInfoPersonneFromAllocine(directorElt, realisateur);
			
			realisateur.addRoleOnFilmByType(film, RoleType.REALISATEUR);
			
			film.getRealisateurs().add(realisateur);
		}
		
		Elements actorsElt = doc.select("li[itemprop=actors]");
		
		for (Element actorElt : actorsElt) {
			Personne acteur = new Personne();
			
			recupererInfoPersonneFromAllocine(actorElt, acteur);
			
			Element actorLinkElt = actorElt.select("a[itemprop=url]").get(0);
			Element roleNameParElt = actorLinkElt.parent().nextElementSibling();
			
			acteur.addRoleOnFilmByType(film, RoleType.ACTEUR);
			
			if (roleNameParElt != null) {
				acteur.getRoles().get(0).setRole(roleNameParElt.text().replaceFirst("Rôle : ", ""));
			}
			film.getActeurs().add(acteur);
		}
		
		Elements actorLineElts = actorsElt.get(0).parent().parent().nextElementSibling().select("tr");
		
		for (Element actorLineElt : actorLineElts) {
			Personne acteur = new Personne();
			
			acteur.addRoleOnFilmByType(film, RoleType.ACTEUR);
			acteur.getRoles().get(0).setRole(actorLineElt.select("td").get(0).text());
			
			Elements actorLinkElts = actorLineElt.select("td").get(1).select("a");
			
			if (actorLinkElts.size() > 0) {
				acteur.setAllocineId(computeAllocineIdFromFicheActeurUrl(actorLinkElts.first().attr("href")));
				acteur.setName(actorLinkElts.get(0).text());
			} else {
				Element actorImgElt = actorLineElt.select("td").get(1).select("img").first();
				
				acteur.setName(actorImgElt.attr("alt"));
			}
			film.getActeurs().add(acteur);
		}
		
		Elements linkPersonElts = doc.select("a[href^=/personne/]");
		
		for (Element linkPersonElt : linkPersonElts) {
			String nom = linkPersonElt.text();
			String role = null;
			
			Elements parents = linkPersonElt.parents();
			
			if (parents != null && parents.size() > 3 && "tr".equalsIgnoreCase(parents.get(2).nodeName())) {
				role = parents.get(2).select("td").get(0).text();
			}
			RoleType roleType = RoleType.getRoleTypeByRoleInAllocine(role);
			
			if (roleType != null) {
				Personne personne = new Personne();
				
				personne.setName(nom);
				personne.setAllocineId(computeAllocineIdFromFicheActeurUrl(linkPersonElt.attr("href")));
				
				personne.addRoleOnFilmByType(film, roleType);
				film.addPersonneParRoleType(personne, roleType);
			}
		}
	}
	
	private void recupererInfoPersonneFromAllocine(Element actorElt, Personne acteur) {
		acteur.setAllocineId(computeAllocineIdFromFicheActeurUrl(actorElt.select("a[itemprop=url]").attr("href")));
		acteur.setName(actorElt.select("span[itemprop=name]").text());
		acteur.setUrlAllocinePortraitMini(actorElt.select("img[itemprop=image]").attr("src"));
	}

		
	@Override
	public Film recupererPlusInfos(ApercuFilm apercuFilm) {
		Film result = new Film(apercuFilm);

		String allocineId = apercuFilm.computeAllocineIdFromUrlAllocineFicheFilm();
				
		recupererInfoFromFicheFilm(result, allocineId);
		recupererInfoFromCastingFilm(result, allocineId);
		
		return result;
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
