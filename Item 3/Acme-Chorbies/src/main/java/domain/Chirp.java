package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Access(AccessType.PROPERTY)
public class Chirp extends DomainEntity{
	
	//Constructors----------------------------------
	
	public Chirp(){
		super();
	}
	
	//Attributes-------------------------------------
	
	private Date sentMoment;
	private String subject;
	private String text;
	private Collection<String> attachments;
	private boolean copy;
	
	@Past
	public Date getSentMoment() {
		return sentMoment;
	}
	public void setSentMoment(Date sentMoment) {
		this.sentMoment = sentMoment;
	}
	
	@NotBlank
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@NotBlank
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	//@URL?
	@NotNull
	public Collection<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(Collection<String> attachments) {
		this.attachments = attachments;
	}
	
	public boolean isCopy() {
		return copy;
	}
	public void setCopy(boolean copy) {
		this.copy = copy;
	}
	
	//Relationships --------------------------------------------------
	
	private Chorbi sender;
	private Chorbi recipient;

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Chorbi getSender() {
		return sender;
	}
	public void setSender(Chorbi sender) {
		this.sender = sender;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Chorbi getRecipient() {
		return recipient;
	}
	public void setRecipient(Chorbi recipient) {
		this.recipient = recipient;
	}
	
	
	

}
