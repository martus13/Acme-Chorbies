
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "Desire")
public class Like extends DomainEntity {

	// Constructors -----------------------------------------------------------
	public Like() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private Date	likeMoment;
	private String	comment;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getLikeMoment() {
		return this.likeMoment;
	}

	public void setLikeMoment(final Date likeMoment) {
		this.likeMoment = likeMoment;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}


	// Relationships ----------------------------------------------------------

	private Chorbi	givenBy;
	private Chorbi	givenTo;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Chorbi getGivenBy() {
		return this.givenBy;
	}

	public void setGivenBy(final Chorbi givenBy) {
		this.givenBy = givenBy;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Chorbi getGivenTo() {
		return this.givenTo;
	}

	public void setGivenTo(final Chorbi givenTo) {
		this.givenTo = givenTo;
	}

}
