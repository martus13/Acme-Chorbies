
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.Chorbi;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CreditCardRepository	creditCardRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ChorbiService			chorbiService;


	// Constructors -----------------------------------------------------------
	public CreditCardService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public CreditCard findOne(final int creditCardId) {
		Assert.isTrue(creditCardId != 0);

		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);

		return result;
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> result;

		result = this.creditCardRepository.findAll();

		return result;
	}

	public CreditCard create() {
		CreditCard result;
		Chorbi chorbi;

		result = new CreditCard();

		chorbi = this.chorbiService.findByPrincipal();

		result.setChorbi(chorbi);

		return result;
	}

	public CreditCard save(CreditCard creditCard) {
		Assert.notNull(creditCard);

		Calendar expirationCalendar, calendar;

		calendar = Calendar.getInstance();
		expirationCalendar = Calendar.getInstance();

		// chorbi de CC = chorbi logueado
		Assert.isTrue(creditCard.getChorbi().equals(this.chorbiService.findByPrincipal()));

		// brand name
		Assert.isTrue(creditCard.getBrandName().equals("VISA") || creditCard.getBrandName().equals("MASTERCARD") || creditCard.getBrandName().equals("DISCOVER") || creditCard.getBrandName().equals("DINNERS") || creditCard.getBrandName().equals("AMEX"));

		// expiration date -> al menos un día más
		expirationCalendar.set(creditCard.getExpirationYear(), creditCard.getExpirationMonth() - 1, 1);
		expirationCalendar.set(Calendar.DAY_OF_MONTH, expirationCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1); // cogemos el último día del mes menos uno, porque tiene que ser un día más

		Assert.isTrue(calendar.before(expirationCalendar) || calendar.equals(expirationCalendar));

		creditCard = this.creditCardRepository.save(creditCard);

		return creditCard;
	}

	public void delete(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		Chorbi chorbi;

		chorbi = this.chorbiService.findByPrincipal();
		Assert.isTrue(chorbi.equals(creditCard.getChorbi()));

		this.creditCardRepository.delete(creditCard);
	}

	// Other business methods -------------------------------------------------

	public CreditCard findByChorbi(final int chorbiId) {
		CreditCard result;

		result = this.creditCardRepository.findByChorbi(chorbiId);

		return result;
	}

	public boolean checkValidation(final CreditCard creditCard) {
		boolean result;
		Calendar calendar;
		Calendar expirationCalendar;

		calendar = Calendar.getInstance();
		expirationCalendar = Calendar.getInstance();

		if (creditCard == null)
			result = false;
		else {
			result = (creditCard.getBrandName().equals("VISA") || creditCard.getBrandName().equals("MASTERCARD") || creditCard.getBrandName().equals("DISCOVER") || creditCard.getBrandName().equals("DINNERS") || creditCard.getBrandName().equals("AMEX"));

			// expiration date -> al menos un día más
			expirationCalendar.set(creditCard.getExpirationYear(), creditCard.getExpirationMonth() - 1, 1);
			expirationCalendar.set(Calendar.DAY_OF_MONTH, expirationCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1); // cogemos el último día del mes menos uno, porque tiene que ser un día más

			result = result && (calendar.before(expirationCalendar) || calendar.equals(expirationCalendar));
		}
		return result;
	}
}
