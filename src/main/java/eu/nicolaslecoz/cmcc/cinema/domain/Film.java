package eu.nicolaslecoz.cmcc.cinema.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Nicolas Le Coz
 * @since 12 septembre 2010
 */
@Entity
@Table(name="cmcc_film")
public class Film {
	@Id @GeneratedValue @Column(name="id_film")
	private Long id;
	private String titre;
	private String titreOriginal;
	private String synopsis;
	private int annee;
	private String urlAllocineAfficheMini;
	private String urlAllocineFicheFilm;
	
	@Transient private final List<GenreType> genres;
	@Transient private final List<Personne> realisateurs;
	@Transient private final List<Personne> acteurs;
	@Transient private final List<Personne> producteurs;
	@Transient private final List<Personne> scenaristes;
	@Transient private final List<Personne> equipeTechnique;
	
	public void addPersonneParRoleType(Personne personne, RoleType roleType) {
		if (roleType == null) {
			throw new IllegalStateException();
		}
		if (RoleType.ACTEUR == roleType) {
			this.acteurs.add(personne);
		} else if (RoleType.PRODUCTEUR == roleType) {
			this.producteurs.add(personne);
		} else if (RoleType.REALISATEUR == roleType) {
			this.realisateurs.add(personne);
		} else if (RoleType.SCENARISTE == roleType) {
			this.scenaristes.add(personne);
		} else if (RoleType.TECHNIQUE == roleType) {
			this.equipeTechnique.add(personne);
		}
	}
	
	public Film() {
		this.genres = new ArrayList<GenreType>();
		this.realisateurs = new ArrayList<Personne>();
		this.acteurs = new ArrayList<Personne>();
		this.producteurs = new ArrayList<Personne>();
		this.scenaristes = new ArrayList<Personne>();
		this.equipeTechnique = new ArrayList<Personne>();
	}

	public Film(ApercuFilm apercuFilm) {
		this();
		this.titre = apercuFilm.getTitre();
		this.titreOriginal = apercuFilm.getTitreOriginal();
		this.annee = apercuFilm.getAnnee();
		this.urlAllocineAfficheMini = apercuFilm.getUrlAllocineAfficheMini();
		this.urlAllocineFicheFilm = apercuFilm.getUrlAllocineFicheFilm();
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
	public List<Personne> getRealisateurs() {
		return realisateurs;
	}
	public List<Personne> getActeurs() {
		return acteurs;
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
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public List<GenreType> getGenres() {
		return genres;
	}
	public void addGenreType(GenreType genreType) {
		this.genres.add(genreType);
	}
	public List<Personne> getProducteurs() {
		return producteurs;
	}
	public List<Personne> getScenaristes() {
		return scenaristes;
	}
	public List<Personne> getEquipeTechnique() {
		return equipeTechnique;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
