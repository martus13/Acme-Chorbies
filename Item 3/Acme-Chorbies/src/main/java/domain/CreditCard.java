
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "expirationMonth"), @Index(columnList = "expirationYear")
})
public class CreditCard extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public CreditCard() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	holderName;
	private String	brandName;
	private String	number;
	private Integer	expirationMonth;
	private Integer	expirationYear;
	private Integer	cvv;


	@NotBlank
	public String getHolderName() {
		return this.holderName;
	}
	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	@NotBlank
	public String getBrandName() {
		return this.brandName;
	}
	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}

	@CreditCardNumber
	@NotBlank
	public String getNumber() {
		return this.number;
	}
	public void setNumber(final String number) {
		this.number = number;
	}

	@NotNull
	@Range(min = 1, max = 12)
	public Integer getExpirationMonth() {
		return this.expirationMonth;
	}
	public void setExpirationMonth(final Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@NotNull
	@Min(2017)
	public Integer getExpirationYear() {
		return this.expirationYear;
	}
	public void setExpirationYear(final Integer expirationYear) {
		this.expirationYear = expirationYear;
	}

	@NotNull
	@Range(min = 100, max = 999)
	public Integer getCvv() {
		return this.cvv;
	}
	public void setCvv(final Integer cvv) {
		this.cvv = cvv;
	}


	// Relationships ----------------------------------------------------------
	private Chorbi	chorbi;


	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Chorbi getChorbi() {
		return this.chorbi;
	}

	public void setChorbi(final Chorbi chorbi) {
		this.chorbi = chorbi;
	}

	//	public static String maskNumber(final String number) {
	//		String result;
	//		int start;
	//		int end;
	//		String stringFill;
	//
	//		start = 4;
	//		end = number.length() - 4;
	//
	//		stringFill = "";
	//		for (int i = 0; i < end - start; i++)
	//			stringFill += "*";
	//
	//		result = StringUtils.overlay(number, stringFill, start, end);
	//
	//		return result;
	//	}

}
