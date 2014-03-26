package eu.nicolaslecoz.cmcc.cinema.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import eu.nicolaslecoz.cmcc.cinema.domain.Film;
import eu.nicolaslecoz.cmcc.cinema.repository.FilmRepository;

/**
 * 
 * @author Nicolas Le Coz
 * @since 25 mai 2011
 */
@Repository
public class FilmRepositoryImpl implements FilmRepository {
	@PersistenceContext
	private EntityManager entityManager;
		
	/*
	 *  enregistrer */
	@Transactional(readOnly=false)
	public Film enregistrerFilm(Film film) {
		entityManager.persist(film);
		return film;
	}
	
	/*
	 * rechercher */
	@Transactional(readOnly=true)
	public Film rechercheFilmParId(Long idFilm) {
		return (Film) entityManager.createQuery("select f from Film f where f.id = :idFilm")
							.setParameter("idFilm", idFilm)
							.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Film> rechercheFilmParId(List<Long> listeIdFilm) {
		return entityManager.createQuery("select f from Film f where f.id in (:listeIdFilm)")
							.setParameter("listeIdFilm", listeIdFilm)
							.getResultList();
	}
	
	/*
	 * effacer */
	@Transactional(readOnly=false)
	public void effacerFilm(List<Long> listeIdFilm) {
		entityManager.createQuery("delete Film f where f.id in (:listeIdFilm)")
						.setParameter("listeIdFilm", listeIdFilm)
						.executeUpdate();
	}
}
