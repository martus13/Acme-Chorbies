
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "relationshipEngage"), @Index(columnList = "banned")
})
public class Chorbi extends Actor {

	// Constructors -----------------------------------------------------------
	public Chorbi() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String				picture;
	private String				description;
	private Genre				genre;
	private Boolean				banned;
	private Date				birthDate;
	private RelationshipType	relationshipEngage;
	private Coordinates			coordinates;


	@NotBlank
	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Valid
	@Enumerated(EnumType.STRING)
	public Genre getGenre() {
		return this.genre;
	}

	public void setGenre(final Genre genre) {
		this.genre = genre;
	}

	@NotNull
	public Boolean getBanned() {
		return this.banned;
	}

	public void setBanned(final Boolean banned) {
		this.banned = banned;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}

	@NotNull
	@Valid
	@Enumerated(EnumType.STRING)
	public RelationshipType getRelationshipEngage() {
		return this.relationshipEngage;
	}

	public void setRelationshipEngage(final RelationshipType relationshipEngage) {
		this.relationshipEngage = relationshipEngage;
	}

	@NotNull
	@Valid
	public Coordinates getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(final Coordinates coordinates) {
		this.coordinates = coordinates;
	}


	// Relationships ----------------------------------------------------------

	private Collection<Like>	givenLikes;
	private Collection<Like>	receivedLikes;
	private Collection<Chirp>	receivedChirps;
	private Collection<Chirp>	sentChirps;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "givenBy")
	public Collection<Like> getGivenLikes() {
		return this.givenLikes;
	}

	public void setGivenLikes(final Collection<Like> givenLikes) {
		this.givenLikes = givenLikes;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "givenTo")
	public Collection<Like> getReceivedLikes() {
		return this.receivedLikes;
	}

	public void setReceivedLikes(final Collection<Like> receivedLikes) {
		this.receivedLikes = receivedLikes;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "recipient")
	public Collection<Chirp> getReceivedChirps() {
		return this.receivedChirps;
	}

	public void setReceivedChirps(final Collection<Chirp> receivedChirps) {
		this.receivedChirps = receivedChirps;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "sender")
	public Collection<Chirp> getSentChirps() {
		return this.sentChirps;
	}

	public void setSentChirps(final Collection<Chirp> sentChirps) {
		this.sentChirps = sentChirps;
	}

}
