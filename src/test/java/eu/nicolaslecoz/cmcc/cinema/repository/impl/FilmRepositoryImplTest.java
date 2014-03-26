package eu.nicolaslecoz.cmcc.cinema.repository.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import eu.nicolaslecoz.cmcc.cinema.domain.ApercuFilm;
import eu.nicolaslecoz.cmcc.cinema.domain.Film;
import eu.nicolaslecoz.cmcc.cinema.repository.FilmRepository;
import eu.nicolaslecoz.cmcc.cinema.service.RechercherInfoAllocine;
import eu.nicolaslecoz.cmcc.cinema.service.RecupererInfoAllocine;

/**
 * 
 * @author Nicolas Le Coz
 * @since 30th may 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/cmcc.spring.xml" })
public class FilmRepositoryImplTest {
	
	@Autowired
	private RechercherInfoAllocine rechercherInfoAllocine;
	
	@Autowired
	private RecupererInfoAllocine recupererInfoAllocine;
	
	@Autowired
	private FilmRepository filmRepository;
	
	@Test
	public void test01() {
		List<ApercuFilm> apercuFilms = rechercherInfoAllocine.rechercher("Forrest Gump");
		
		Film film = recupererInfoAllocine.recupererPlusInfos(apercuFilms.get(0));
		
		Assert.assertNotNull(film);
		
		filmRepository.enregistrerFilm(film);
	}
}
