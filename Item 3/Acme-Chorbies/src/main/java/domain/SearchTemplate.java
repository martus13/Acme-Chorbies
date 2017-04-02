
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class SearchTemplate extends DomainEntity {

	// Constructors -----------------------------------------------------------
	public SearchTemplate() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private RelationshipType	relationshipType;
	private Integer				approximateAge;
	private String				singleKeyword;
	private Genre				genre;
	private Coordinates			coordinates;
	private Date				searchTime;


	@Valid
	@Enumerated(EnumType.STRING)
	public RelationshipType getRelationshipType() {
		return this.relationshipType;
	}

	public void setRelationshipType(final RelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}

	@Min(18)
	public Integer getApproximateAge() {
		return this.approximateAge;
	}

	public void setApproximateAge(final Integer approximateAge) {
		this.approximateAge = approximateAge;
	}

	public String getSingleKeyword() {
		return this.singleKeyword;
	}

	public void setSingleKeyword(final String singleKeyword) {
		this.singleKeyword = singleKeyword;
	}

	@Valid
	@Enumerated(EnumType.STRING)
	public Genre getGenre() {
		return this.genre;
	}

	public void setGenre(final Genre genre) {
		this.genre = genre;
	}

	@Valid
	public Coordinates getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(final Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getSearchTime() {
		return this.searchTime;
	}

	public void setSearchTime(final Date searchTime) {
		this.searchTime = searchTime;
	}


	// Relationships ----------------------------------------------------------

	private Chorbi				chorbi;
	private Collection<Chorbi>	results;


	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Chorbi getChorbi() {
		return this.chorbi;
	}

	public void setChorbi(final Chorbi chorbi) {
		this.chorbi = chorbi;
	}

	@NotNull
	@Valid
	@ManyToMany()
	public Collection<Chorbi> getResults() {
		return this.results;
	}

	public void setResults(final Collection<Chorbi> results) {
		this.results = results;
	}

}
