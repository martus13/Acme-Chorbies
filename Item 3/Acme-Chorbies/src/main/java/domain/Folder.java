package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity{

	
	//COnstructor-------------------------------------------
	
	public Folder(){
		super();
	}
	
	//Attributes-------------------------------------------
	
	private String name;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//Relationships------------------------------------------
	
	private Chorbi chorbi;
	private Collection<Chirp> chirps;

	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Chorbi getChorbi() {
		return chorbi;
	}

	public void setChorbi(Chorbi chorbi) {
		this.chorbi = chorbi;
	}

	
	@Valid
	@NotNull
	@OneToMany(mappedBy="folder")
	public Collection<Chirp> getChirps() {
		return chirps;
	}

	public void setChirps(Collection<Chirp> chirps) {
		this.chirps = chirps;
	}
	
	
	
}
