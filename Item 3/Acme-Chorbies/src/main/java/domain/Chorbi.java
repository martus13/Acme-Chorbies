
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
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
	private CreditCard			creditCard;


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
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
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

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}


	// Relationships ----------------------------------------------------------

	private Collection<Like>	givenLikes;
	private Collection<Like>	receivedLikes;
	private Collection<Folder>	folders;


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
	@NotEmpty
	@OneToMany(mappedBy = "chorbi")
	public Collection<Folder> getFolders() {
		return this.folders;
	}

	public void setFolders(final Collection<Folder> folders) {
		this.folders = folders;
	}

	public void addFolder(final Folder folder) {
		this.folders.add(folder);
	}

	public void removeFolder(final Folder folder) {
		this.folders.remove(folder);
	}

}
