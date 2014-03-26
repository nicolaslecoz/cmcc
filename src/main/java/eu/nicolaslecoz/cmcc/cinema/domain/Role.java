package eu.nicolaslecoz.cmcc.cinema.domain;

/**
 * 
 * @author Nicolas Le Coz
 * @since 14 septembre 2010
 */
public class Role {
	private Personne personne;
	private Film film;
	private String role;
	private RoleType roleType;
	
	public Personne getPersonne() {
		return personne;
	}
	public void setPersonne(Personne personne) {
		this.personne = personne;
	}
	public Film getFilm() {
		return film;
	}
	public void setFilm(Film film) {
		this.film = film;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public RoleType getRoleType() {
		return roleType;
	}
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
}
