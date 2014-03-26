package eu.nicolaslecoz.cmcc.cinema.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicolas Le Coz
 * @since 8 septembre 2010
 */
public class ApercuFilm {
	private String titre;
	private String titreOriginal;
	private int annee;
	private String urlAllocineAfficheMini;
	private String urlAllocineFicheFilm;
	private final List<String> realisateurs = new ArrayList<String>();
	private final List<String> acteurs = new ArrayList<String>();
	
	/**
	 * Format http://www.allocine.fr/film/fichefilm_gen_cfilm=[id].html
	 */
	public String computeAllocineIdFromUrlAllocineFicheFilm() {
		if (this.urlAllocineFicheFilm == null) {
			return null;
		}
		
		int begin = this.urlAllocineFicheFilm.lastIndexOf("=");
		int end = this.urlAllocineFicheFilm.lastIndexOf(".");
		
		if (begin == -1 || end == -1) {
			return null;
		}
		return this.urlAllocineFicheFilm.substring(begin + 1, end);
	}
	
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getTitreOriginal() {
		return titreOriginal;
	}
	public void setTitreOriginal(String titreOriginal) {
		this.titreOriginal = titreOriginal;
	}
	public List<String> getRealisateurs() {
		return realisateurs;
	}
	public void addRealisateur(String realisateur) {
		this.realisateurs.add(realisateur);
	}
	public List<String> getActeurs() {
		return acteurs;
	}
	public void addActeur(String acteur) {
		this.acteurs.add(acteur);
	}
	public int getAnnee() {
		return annee;
	}
	public void setAnnee(int annee) {
		this.annee = annee;
	}
	public String getUrlAllocineAfficheMini() {
		return urlAllocineAfficheMini;
	}
	public void setUrlAllocineAfficheMini(String urlAllocineAfficheMini) {
		this.urlAllocineAfficheMini = urlAllocineAfficheMini;
	}
	public String getUrlAllocineFicheFilm() {
		return urlAllocineFicheFilm;
	}
	public void setUrlAllocineFicheFilm(String urlAllocineFicheFilm) {
		this.urlAllocineFicheFilm = urlAllocineFicheFilm;
	}
}
