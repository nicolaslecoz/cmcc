package eu.nicolaslecoz.cmcc.cinema.domain;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Nicolas Le Coz
 * @since 15 septembre 2010
 */
public enum RoleType {
	ACTEUR, 
	REALISATEUR, 
	SCENARISTE, 
	PRODUCTEUR,
	TECHNIQUE;
	
	public static final RoleType getRoleTypeByRoleInAllocine(String allocineRole) {
		if (StringUtils.isEmpty(allocineRole)) {
			return null;
		} else if ("productrice".equalsIgnoreCase(allocineRole)
				|| "producteur".equalsIgnoreCase(allocineRole)
				|| "coproducteur".equalsIgnoreCase(allocineRole)) {
			return PRODUCTEUR;
		} else if ("scénariste".equalsIgnoreCase(allocineRole)) {
			return SCENARISTE;
		} else if ("monteur son".equalsIgnoreCase(allocineRole)
				|| "superviseur des effets visuels".equalsIgnoreCase(allocineRole)
				|| "compositeur".equalsIgnoreCase(allocineRole)
				|| "directeur de la photographie".equalsIgnoreCase(allocineRole)
				|| "monteur".equalsIgnoreCase(allocineRole)
				|| "chef décorateur".equalsIgnoreCase(allocineRole)
				|| "chef décoratrice".equalsIgnoreCase(allocineRole)
				|| "maquilleuse".equalsIgnoreCase(allocineRole)
				|| "maquilleur".equalsIgnoreCase(allocineRole)
				|| "Ingénieur du son".equalsIgnoreCase(allocineRole)) {
			return TECHNIQUE;
		}		
		return null;
	}
}
