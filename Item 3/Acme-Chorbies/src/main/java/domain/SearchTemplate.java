
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class SearchTemplate extends DomainEntity {

	// Constructors -----------------------------------------------------------
	public SearchTemplate() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private RelationshipType	relationshipType;

	// Relationships ----------------------------------------------------------
}
