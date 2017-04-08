
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Chorbi;
import domain.Coordinates;
import domain.CreditCard;
import domain.Genre;
import domain.RelationshipType;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChorbiServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ChorbiService			chorbiService;

	@Autowired
	private SearchTemplateService	searchTemplateService;


	// Tests ------------------------------------------------------------------

	// A continuación se van a realizar pruebas para comprobar el correcto funcionamiento de los casos de uso relacionados con Chorbi.

	// Registro de un chorbi:
	@Test
	public void driverCreate() {
		Calendar correctCalendar;
		Calendar wrongCalendar;

		correctCalendar = Calendar.getInstance();
		correctCalendar.set(1992, 9, 31, 6, 0, 0);

		wrongCalendar = Calendar.getInstance();
		wrongCalendar.set(Calendar.YEAR, wrongCalendar.get(Calendar.YEAR) - 17); // Hace 17 años

		wrongCalendar = Calendar.getInstance();

		final Object testingData[][] = {
			{	// Bien
				"prueba1", "3f1b7ccad63d40a7b4c27dda225bf941", "prueba", "1", "prueba1@gmail.com", "1234",
				"http://kids.nationalgeographic.com/content/dam/kids/photos/articles/Other%20Explore%20Photos/H-P/International%20Photography%20Contest/IPC-winner-tile.jpg", "Descripcion prueba 1", Genre.man, correctCalendar.getTime(),
				RelationshipType.activities, "España", "Sevilla", null
			}, {// Menor de edad
				"prueba1", "3f1b7ccad63d40a7b4c27dda225bf941", "prueba", "1", "prueba1@gmail.com", "1234",
				"http://kids.nationalgeographic.com/content/dam/kids/photos/articles/Other%20Explore%20Photos/H-P/International%20Photography%20Contest/IPC-winner-tile.jpg", "Descripcion prueba 1", Genre.man, wrongCalendar.getTime(),
				RelationshipType.activities, "España", "Sevilla", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testCreate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(Genre) testingData[i][8], (Date) testingData[i][9], (RelationshipType) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (Class<?>) testingData[i][13]);
	}

	// Edición CreditCard:
	@Test
	public void driverEditCreditCard() {

		final Object testingData[][] = {
			{ // Bien
				49, "MASTERCARD", "Holder name test", "5379721258203853", 7, 2017, 159, null
			}, { // Error brand name
				46, "Brand name erroneo", "Holder name test", "5379721258203853", 7, 2017, 159, IllegalArgumentException.class
			}, { // Error expiration month
				46, "MASTERCARD", "Holder name test", "5379721258203853", 3, 2017, 159, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testEditCreditCard((int) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (Integer) testingData[i][5], (Integer) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}

	// Baneado/desbaneado de un chorbi:
	@Test
	public void driverBanUnban() {

		final Object testingData[][] = {
			{ // Bien
				"admin", 49, null
			}, { // Error autenticación
				null, 49, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testBan((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
			this.testUnban((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void testCreate(final String username, final String password, final String name, final String surname, final String email, final String phoneNumber, final String picture, final String description, final Genre genre, final Date birthDate,
		final RelationshipType relationshipEngage, final String country, final String city, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			Chorbi chorbi;
			Coordinates coordinates;

			chorbi = this.chorbiService.create();

			coordinates = new Coordinates();
			coordinates.setCountry(country);
			coordinates.setCity(city);

			chorbi.getUserAccount().setUsername(username);
			chorbi.getUserAccount().setPassword(password);
			chorbi.setName(name);
			chorbi.setSurname(surname);
			chorbi.setEmail(email);
			chorbi.setPhoneNumber(phoneNumber);
			chorbi.setPicture(picture);
			chorbi.setDescription(description);
			chorbi.setGenre(genre);
			chorbi.setBirthDate(birthDate);
			chorbi.setRelationshipEngage(relationshipEngage);
			chorbi.setCoordinates(coordinates);

			chorbi = this.chorbiService.save(chorbi);
			Assert.notNull(this.searchTemplateService.findByChorbiId(chorbi));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	protected void testEditCreditCard(final int chorbiId, final String brandName, final String holderName, final String number, final Integer expirationMonth, final Integer expirationYear, final Integer cvv, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			Chorbi chorbi;
			CreditCard creditCard;

			creditCard = new CreditCard();
			creditCard.setBrandName(brandName);
			creditCard.setHolderName(holderName);
			creditCard.setNumber(number);
			creditCard.setExpirationMonth(expirationMonth);
			creditCard.setExpirationYear(expirationYear);
			creditCard.setCvv(cvv);

			chorbi = this.chorbiService.findOne(chorbiId);
			chorbi.setCreditCard(creditCard);

			chorbi = this.chorbiService.save(chorbi);
			Assert.notNull(chorbi.getCreditCard());

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	protected void testBan(final String username, final int chorbiId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			Chorbi chorbi;

			chorbi = this.chorbiService.findOne(chorbiId);

			chorbi = this.chorbiService.ban(chorbi);
			Assert.isTrue(chorbi.getBanned());

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	protected void testUnban(final String username, final int chorbiId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			Chorbi chorbi;

			chorbi = this.chorbiService.findOne(chorbiId);

			chorbi = this.chorbiService.unban(chorbi);
			Assert.isTrue(!chorbi.getBanned());

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	/////////////// Sin driver:
	@Test
	public void testFindAll() {
		Collection<Chorbi> chorbies;

		chorbies = this.chorbiService.findAll();
		Assert.isTrue(chorbies.size() == 5);
	}

	@Test
	public void testFindNotBanned() {
		Collection<Chorbi> results;

		results = this.chorbiService.findNotBanned();
		Assert.isTrue(results.size() == 5);

	}

	@Test
	public void testFindGroupByCountryAndCity() {
		Collection<Object[]> results;

		results = this.chorbiService.findGroupByCountryAndCity();
		Assert.isTrue(results.size() == 2);

	}

	@Test
	public void testFindMinMaxAvgAges() {
		Object[] results;

		results = this.chorbiService.findMinMaxAvgAges();
		System.out.println("testFindMinMaxAvgAges --> Min: " + results[0] + ", Max: " + results[1] + ", Avg: " + results[2]);

	}

	@Test
	public void testFindAllSortedByReceivedLikes() {
		Collection<Chorbi> results;

		results = this.chorbiService.findAllSortedByReceivedLikes();
		Assert.isTrue(results.size() == 5);

	}

	@Test
	public void testFindRatioCreditCard() {
		Double result;

		result = this.chorbiService.findRatioCreditCard();
		System.out.println("testFindRatioCreditCard: " + result);

	}
	@Test
	public void testFindRatioActivitiesLoveFriendship() {
		Double[] results;

		results = this.chorbiService.findRatioActivitiesLoveFriendship();
		System.out.println("testFindRatioActivitiesLoveFriendship\nMin: " + results[0] + ", Max: " + results[1] + ", Avg: " + results[2]);

	}
}
