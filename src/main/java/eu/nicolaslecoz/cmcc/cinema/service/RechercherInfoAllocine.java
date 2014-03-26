package eu.nicolaslecoz.cmcc.cinema.service;

import java.util.List;

import eu.nicolaslecoz.cmcc.cinema.domain.ApercuFilm;

/**
 * 
 * @author Nicolas LE COZ <lecoz.nicolas@gmail.com>
 * @since 6 septembre 2010
 */
public interface RechercherInfoAllocine {
	public List<ApercuFilm> rechercher(String recherche);
}
