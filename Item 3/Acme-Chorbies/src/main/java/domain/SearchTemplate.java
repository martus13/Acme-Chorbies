
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
	private Date				searchTime;
	private String				country;
	private String				state;
	private String				province;
	private String				city;


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

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	public Date getSearchTime() {
		return this.searchTime;
	}

	public void setSearchTime(final Date searchTime) {
		this.searchTime = searchTime;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getState() {
		return this.state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(final String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
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
