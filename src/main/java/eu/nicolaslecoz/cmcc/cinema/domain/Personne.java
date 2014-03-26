package eu.nicolaslecoz.cmcc.cinema.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Nicolas Le Coz
 * @since 15 septembre 2010
 */
public class Personne {
	private String name; // Eventuellement une serie de nom
	private List<Role> roles;
	private String allocineId;
	private String urlAllocinePortraitMini;
	
	public void addRoleOnFilmByType(Film film, RoleType roleType)  {
		Role role = new Role();
		
		role.setFilm(film);
		role.setRoleType(roleType);
		role.setPersonne(this);
		
		this.roles.add(role);
	}
	
	public Personne() {
		this.roles = new ArrayList<Role>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void addRole(Role role) {
		this.roles.add(role);
	}
	public String getAllocineId() {
		return allocineId;
	}
	public void setAllocineId(String allocineId) {
		this.allocineId = allocineId;
	}
	public String getUrlAllocinePortraitMini() {
		return urlAllocinePortraitMini;
	}
	public void setUrlAllocinePortraitMini(String urlAllocinePortraitMini) {
		this.urlAllocinePortraitMini = urlAllocinePortraitMini;
	}
}
