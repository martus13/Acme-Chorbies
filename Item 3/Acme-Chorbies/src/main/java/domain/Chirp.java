
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "copy")
})
public class Chirp extends DomainEntity {

	//Constructors----------------------------------

	public Chirp() {
		super();
	}


	//Attributes-------------------------------------

	private Date				sentMoment;
	private String				subject;
	private String				text;
	private Collection<String>	attachments;
	private boolean				copy;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getSentMoment() {
		return this.sentMoment;
	}
	public void setSentMoment(final Date sentMoment) {
		this.sentMoment = sentMoment;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}
	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}
	public void setText(final String text) {
		this.text = text;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getAttachments() {
		return this.attachments;
	}
	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}

	@NotNull
	public boolean getCopy() {
		return this.copy;
	}
	public void setCopy(final boolean copy) {
		this.copy = copy;
	}


	//Relationships --------------------------------------------------

	private Chorbi	sender;
	private Chorbi	recipient;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Chorbi getSender() {
		return this.sender;
	}
	public void setSender(final Chorbi sender) {
		this.sender = sender;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Chorbi getRecipient() {
		return this.recipient;
	}
	public void setRecipient(final Chorbi recipient) {
		this.recipient = recipient;
	}

}
