package eu.nicolaslecoz.cmcc.cinema.service.impl;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import eu.nicolaslecoz.cmcc.cinema.domain.ApercuFilm;
import eu.nicolaslecoz.cmcc.cinema.service.RechercherInfoAllocine;

/**
 * 
 * @author Nicolas Le Coz
 * @since 8th september 2010
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/cmcc.spring.xml" })
public class RechercheInfoAllocineImplTest {

	@Autowired
	private RechercherInfoAllocine rechercherInfoAllocine;

	private void assertCommonFilm(ApercuFilm film1, String expectedTitre, int expectedAnnee, 
			String expectedUrlAllocineAfficheMini, String expectedUrlAllocineFicheFilm,
			String[] expectedRealisateurs, String[] expectedActeurs) {
		
		Assert.assertEquals(expectedTitre, film1.getTitre());
		
		Assert.assertEquals(expectedAnnee, film1.getAnnee());

		Assert.assertEquals(expectedUrlAllocineAfficheMini, film1.getUrlAllocineAfficheMini());
		
		Assert.assertEquals(expectedUrlAllocineFicheFilm, film1.getUrlAllocineFicheFilm());
		
		for (String expectedRealisateur : expectedRealisateurs) {
			Assert.assertTrue(film1.getRealisateurs().contains(expectedRealisateur));
		}
		for (String expectedActeur : expectedActeurs) {
			Assert.assertTrue(film1.getActeurs().contains(expectedActeur));
		}
	}

	private void assertFilm(ApercuFilm film, String expectedTitre, int expectedAnnee, 
			String expectedUrlAllocineAfficheMini, String expectedUrlAllocineFicheFilm,
			String[] expectedRealisateurs, String[] expectedActeurs) {
		
		assertCommonFilm(film, expectedTitre, expectedAnnee, expectedUrlAllocineAfficheMini, 
				expectedUrlAllocineFicheFilm, expectedRealisateurs, expectedActeurs);
		Assert.assertNull(film.getTitreOriginal());
	}

	private void assertFilm(ApercuFilm film, String expectedTitre, String expectedTitreOriginal, int expectedAnnee, 
			String expectedUrlAllocineAfficheMini, String expectedUrlAllocineFicheFilm,
			String[] expectedRealisateurs, String[] expectedActeurs) {
		
		assertCommonFilm(film, expectedTitre, expectedAnnee, expectedUrlAllocineAfficheMini, 
				expectedUrlAllocineFicheFilm, expectedRealisateurs, expectedActeurs);
		
		Assert.assertEquals(expectedTitreOriginal, film.getTitreOriginal());
	}
	
	@Test
	public void test01RechercheSimplePersepolis() {
		List<ApercuFilm> result = rechercherInfoAllocine.rechercher("persepolis");
		
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		
		assertFilm(result.get(0), "Persepolis", 2007,
				"http://images.allocine.fr/r_75_106/medias/nmedia/18/63/80/43/18761581.jpg",
				"/film/fichefilm_gen_cfilm=110204.html",
				new String[] {"Marjane Satrapi", "Vincent Paronnaud"},
				new String[] {"Iggy Pop", "Gena Rowlands"});
		
		assertFilm(result.get(1), "Persepolis", 0,
				"http://images.allocine.fr/r_75_106/commons/emptymedia/Affichette_Recherche.gif",
				"/film/fichefilm_gen_cfilm=129091.html",
				new String[] {"Solmaz Shahbazi"},
				new String[] {});
	}

	@Test
	public void test01RechercheAvecUnEspaceForrestrGump() {
		List<ApercuFilm> result = rechercherInfoAllocine.rechercher("forrest gump");
		
		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.size());
		
		assertFilm(result.get(0), "Forrest Gump", 1994,
				"http://images.allocine.fr/r_75_106/medias/nmedia/18/63/30/77/18686566.jpg",
				"/film/fichefilm_gen_cfilm=10568.html",
				new String[] {"Robert Zemeckis"},
				new String[] {"Tom Hanks", "Gary Sinise"});
	}
	
	@Test
	public void test01RechercheAvecUneApostrophe() {
		List<ApercuFilm> result = rechercherInfoAllocine.rechercher("l'arnacoeur");
		
		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.size());
		
		assertFilm(result.get(0), "L'Arnacoeur", 2010,
				"http://images.allocine.fr/r_75_106/medias/nmedia/18/72/58/93/19256845.jpg",
				"/film/fichefilm_gen_cfilm=148441.html",
				new String[] {"Pascal Chaumeil"},
				new String[] {"Romain Duris", "Vanessa Paradis"});
	}

	@Test
	public void test01RechercheAvecDesAccents() {
		List<ApercuFilm> result = rechercherInfoAllocine.rechercher("Tragique décision");
		
		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.size());
		
		assertFilm(result.get(0), "Tragique décision", "Command decision", 1948,
				"http://images.allocine.fr/r_75_106/commons/emptymedia/Affichette_Recherche.gif",
				"/film/fichefilm_gen_cfilm=50611.html",
				new String[] {"Sam Wood"},
				new String[] {"Clark Gable", "Brian Donlevy"});
	}
}
