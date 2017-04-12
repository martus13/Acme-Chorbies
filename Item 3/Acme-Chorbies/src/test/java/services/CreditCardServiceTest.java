
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CreditCardServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CreditCardService	creditCardService;


	// Tests ------------------------------------------------------------------

	// A continuación se van a realizar pruebas para comprobar el correcto funcionamiento de los casos de uso relacionados con CreditCard.

	// Creación CreditCard:
	@Test
	public void driverEditCreditCard() {

		final Object testingData[][] = {
			{ // Bien
				"chorbi1", 55, "MASTERCARD", "Holder name test", "5379721258203853", 7, 2017, 159, null
			}, { // Error brand name
				"chorbi1", 56, "Brand name erroneo", "Holder name test", "5379721258203853", 7, 2017, 159, IllegalArgumentException.class
			}, { // Error expiration month
				"chorbi1", 57, "MASTERCARD", "Holder name test", "5379721258203853", 3, 2017, 159, IllegalArgumentException.class
			}, { // Error expiration month
				null, 55, "MASTERCARD", "Holder name test", "5379721258203853", 3, 2017, 159, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testCreate((String) testingData[i][0], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Integer) testingData[i][5], (Integer) testingData[i][6], (Integer) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void testCreate(final String username, final String brandName, final String holderName, final String number, final Integer expirationMonth, final Integer expirationYear, final Integer cvv, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			CreditCard creditCard;

			creditCard = this.creditCardService.create();
			creditCard.setBrandName(brandName);
			creditCard.setHolderName(holderName);
			creditCard.setNumber(number);
			creditCard.setExpirationMonth(expirationMonth);
			creditCard.setExpirationYear(expirationYear);
			creditCard.setCvv(cvv);

			creditCard = this.creditCardService.save(creditCard);
			Assert.notNull(creditCard);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	protected void testEdit(final String username, final int creditCardId, final String brandName, final String holderName, final String number, final Integer expirationMonth, final Integer expirationYear, final Integer cvv, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			CreditCard creditCard;

			creditCard = this.creditCardService.findOne(creditCardId);
			creditCard.setHolderName(holderName);

			creditCard = this.creditCardService.save(creditCard);
			Assert.notNull(creditCard);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
