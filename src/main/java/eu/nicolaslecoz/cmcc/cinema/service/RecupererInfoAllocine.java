package eu.nicolaslecoz.cmcc.cinema.service;

import eu.nicolaslecoz.cmcc.cinema.domain.ApercuFilm;
import eu.nicolaslecoz.cmcc.cinema.domain.Film;

/**
 * 
 * @author Nicolas LE COZ <lecoz.nicolas@gmail.com>
 * @since 12 septembre 2010
 */
public interface RecupererInfoAllocine {
	public Film recupererPlusInfos(ApercuFilm apercuFilm);
}
