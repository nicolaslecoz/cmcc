package eu.nicolaslecoz.cmcc.cinema.repository;

import java.util.List;

import eu.nicolaslecoz.cmcc.cinema.domain.Film;

/**
 * 
 * @author Nicolas Le Coz
 * @since 25 mai 2011
 */
public interface FilmRepository {
	/*
	 *  enregistrer */
	Film enregistrerFilm(Film film);
	
	/*
	 * rechercher */
	Film rechercheFilmParId(Long idFilm);
	List<Film> rechercheFilmParId(List<Long> listeIdFilm);
	
	/*
	 * effacer */
	void effacerFilm(List<Long> listeIdFilm);
}
