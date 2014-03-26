package eu.nicolaslecoz.cmcc.cinema.service.impl;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import eu.nicolaslecoz.cmcc.cinema.domain.ApercuFilm;
import eu.nicolaslecoz.cmcc.cinema.domain.Film;
import eu.nicolaslecoz.cmcc.cinema.domain.GenreType;
import eu.nicolaslecoz.cmcc.cinema.domain.Personne;
import eu.nicolaslecoz.cmcc.cinema.domain.Role;
import eu.nicolaslecoz.cmcc.cinema.domain.RoleType;
import eu.nicolaslecoz.cmcc.cinema.service.RechercherInfoAllocine;
import eu.nicolaslecoz.cmcc.cinema.service.RecupererInfoAllocine;

/**
 * 
 * @author Nicolas LE COZ <lecoz.nicolas@gmail.com>
 * @since 12 septembre 2010
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/cmcc.spring.xml" })
public class RecupererInAllocineImplTest {

	@Autowired
	private RechercherInfoAllocine rechercherInfoAllocine;
	
	@Autowired
	private RecupererInfoAllocine recupererInfoAllocine;
	
	@Test
	public void test01Persepolis() {
		List<ApercuFilm> apercuFilms = rechercherInfoAllocine.rechercher("persepolis");
		
		Film result = recupererInfoAllocine.recupererPlusInfos(apercuFilms.get(0));
		
		Assert.assertNotNull(result);
		
		Assert.assertEquals("Téhéran 1978 : Marjane, huit ans, songe à l'avenir et se rêve en prophète sauvant " +
				"le monde. Choyée par des parents modernes et cultivés, particulièrement liée à sa grand-mère, elle " +
				"suit avec exaltation les évènements qui vont mener à la révolution et provoquer la chute du régime " +
				"du Chah.Avec l'instauration de la République islamique débute le temps des \"commissaires de la " +
				"révolution\" qui contrôlent tenues et comportements. Marjane qui doit porter le voile, se rêve " +
				"désormais en révolutionnaire.Bientôt, la guerre contre l'Irak entraîne bombardements, privations, " +
				"et disparitions de proches. La répression intérieure devient chaque jour plus sévère.Dans un contexte " +
				"de plus en plus pénible, sa langue bien pendue et ses positions rebelles deviennent problématiques. " +
				"Ses parents décident alors de l'envoyer en Autriche pour la protéger.A Vienne, Marjane vit à quatorze " +
				"ans sa deuxième révolution : l'adolescence, la liberté, les vertiges de l'amour mais aussi l'exil, " +
				"la solitude et la différence.",  result.getSynopsis());
		
		Assert.assertTrue(result.getGenres().contains(GenreType.ANIMATION));
	}
	
	private void AssertPersonne(Personne p, RoleType expectedRoleType) {
		Assert.assertNotNull(p);
//		Assert.assertNotNull(p.getAllocineId());
		Assert.assertNotNull(p.getRoles());
		Assert.assertTrue(p.getRoles().size() > 0);
		
		for (Role r : p.getRoles()) {
			Assert.assertNotNull(r.getPersonne());
			Assert.assertNotNull(r.getRoleType());
			Assert.assertEquals(expectedRoleType, r.getRoleType());
			Assert.assertNotNull(r.getFilm());
		}
	}
	
	private void AssertPersonne(Personne p, RoleType expectedRoleType, 
			String expectedName, String expectedAllocineId, String expectedUrlAllocinePortraitMini) {
		AssertPersonne(p, expectedRoleType);
		Assert.assertEquals(expectedName, p.getName());
		Assert.assertEquals(expectedAllocineId, p.getAllocineId());
		Assert.assertEquals(expectedUrlAllocinePortraitMini, p.getUrlAllocinePortraitMini());
	}

	private void AssertPersonne(Personne p, RoleType expectedRoleType, 
			String expectedName, String expectedAllocineId, String expectedUrlAllocinePortraitMini,
			String expectedRole) {
		AssertPersonne(p, expectedRoleType, expectedName, expectedAllocineId, expectedUrlAllocinePortraitMini);
		
		Assert.assertNotNull(p.getRoles());
		Assert.assertEquals(1, p.getRoles().size());
		Assert.assertEquals(expectedRole, p.getRoles().get(0));
	}
	
	@Test
	public void test02ForrestGump() {
		List<ApercuFilm> apercuFilms = rechercherInfoAllocine.rechercher("Forrest Gump");
		
		Film result = recupererInfoAllocine.recupererPlusInfos(apercuFilms.get(0));
		
		Assert.assertNotNull(result);
		
		Assert.assertTrue(result.getGenres().contains(GenreType.ROMANCE));
		Assert.assertTrue(result.getGenres().contains(GenreType.COMEDIE_DRAMATIQUE));

		Assert.assertEquals("Quelques décennies d'histoire américaine, des années 1940 à la fin du XXème siècle, à " +
				"travers le regard et l'étrange odyssée d'un homme simple et pur, Forrest Gump.", result.getSynopsis());
		
		Assert.assertNotNull(result.getRealisateurs());
		Assert.assertEquals(1, result.getRealisateurs().size());
		
		for (Personne p : result.getRealisateurs()) {
			AssertPersonne(p, RoleType.REALISATEUR, "Robert Zemeckis", "1457", 
					"http://images.allocine.fr/r_120_160/b_1_d6d6d6/medias/nmedia/18/35/48/88/19157501.jpg");
		}
		
		Assert.assertNotNull(result.getActeurs());
		Assert.assertEquals(27, result.getActeurs().size());

		for (Personne p : result.getActeurs()) {
			AssertPersonne(p, RoleType.ACTEUR);
		}
		
		Assert.assertNotNull(result.getProducteurs());
		Assert.assertEquals(4, result.getProducteurs().size());

		for (Personne p : result.getProducteurs()) {
			AssertPersonne(p, RoleType.PRODUCTEUR);
		}
		
		Assert.assertNotNull(result.getScenaristes());
		Assert.assertEquals(1, result.getScenaristes().size());

		for (Personne p : result.getScenaristes()) {
			AssertPersonne(p, RoleType.SCENARISTE);
		}
		
		Assert.assertNotNull(result.getEquipeTechnique());
		Assert.assertEquals(6, result.getEquipeTechnique().size());

		for (Personne p : result.getEquipeTechnique()) {
			AssertPersonne(p, RoleType.TECHNIQUE);
		}
	}
}
