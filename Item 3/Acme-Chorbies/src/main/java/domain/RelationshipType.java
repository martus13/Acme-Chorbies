
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class RelationshipType extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public RelationshipType() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	value;


	@NotBlank
	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

}
