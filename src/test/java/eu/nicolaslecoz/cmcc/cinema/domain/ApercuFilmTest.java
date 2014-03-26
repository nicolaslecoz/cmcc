package eu.nicolaslecoz.cmcc.cinema.domain;

import junit.framework.Assert;

import org.junit.Test;

import eu.nicolaslecoz.cmcc.cinema.domain.ApercuFilm;

/**
 * 
 * @author Nicolas LE COZ <lecoz.nicolas@gmail.com>
 * @since 12 septembre 2010
 */
public class ApercuFilmTest {
	
	private ApercuFilm buildApercuFilm(String url) {
		ApercuFilm result = new ApercuFilm();
		
		result.setUrlAllocineFicheFilm(url);
		return result;
	}
	
	@Test
	public void test01() {
		Assert.assertEquals("110204", 
				buildApercuFilm("http://www.allocine.fr/film/fichefilm_gen_cfilm=110204.html").computeAllocineIdFromUrlAllocineFicheFilm());
		Assert.assertEquals("110204", 
				buildApercuFilm("/film/fichefilm_gen_cfilm=110204.html").computeAllocineIdFromUrlAllocineFicheFilm());
		Assert.assertEquals("1", 
				buildApercuFilm("http://www.allocine.fr/film/fichefilm_gen_cfilm=1.html").computeAllocineIdFromUrlAllocineFicheFilm());
		Assert.assertEquals("1", 
				buildApercuFilm("/film/fichefilm_gen_cfilm=1.html").computeAllocineIdFromUrlAllocineFicheFilm());
	}
}
