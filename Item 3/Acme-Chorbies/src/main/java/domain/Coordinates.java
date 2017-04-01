
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Coordinates {

	//Constructor----------------------------------------------------

	public Coordinates() {
		super();
	}


	//Attributes -------------------------------------------------

	private String	country;
	private String	state;
	private String	provice;
	private String	city;


	@NotBlank
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

	public String getProvice() {
		return this.provice;
	}
	public void setProvice(final String provice) {
		this.provice = provice;
	}

	@NotBlank
	public String getCity() {
		return this.city;
	}
	public void setCity(final String city) {
		this.city = city;
	}

	// toString ---------------------------------------------------------------

	@Override
	public String toString() {
		return "Coordinates [country=" + this.country + ", state=" + this.state + ", provice=" + this.provice + ", city=" + this.city + "]";
	}

}
