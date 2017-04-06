
package services;

import java.util.Calendar;
import java.util.Collection;

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
	/////////////// Con driver:
	/////////////// Sin driver:
	@Test
	public void testFindOne() {
		Chorbi chorbi;

		chorbi = this.chorbiService.findOne(46);
		Assert.notNull(chorbi);
	}

	@Test
	public void testFindAll() {
		Collection<Chorbi> chorbies;

		chorbies = this.chorbiService.findAll();
		Assert.isTrue(chorbies.size() == 5);
	}

	@Test
	public void testCreate() {
		Chorbi chorbi;

		chorbi = this.chorbiService.create();
		Assert.notNull(chorbi);

	}

	@Test
	public void testCreateAndSave() {
		Chorbi chorbi;
		Calendar calendar;
		Coordinates coordinates;

		chorbi = this.chorbiService.create();

		calendar = Calendar.getInstance();
		calendar.set(1992, 9, 31, 6, 0, 0);
		coordinates = new Coordinates();
		coordinates.setCountry("España");
		coordinates.setCity("Sevilla");

		chorbi.getUserAccount().setUsername("prueba1");
		chorbi.getUserAccount().setPassword("3f1b7ccad63d40a7b4c27dda225bf941");
		chorbi.setName("prueba");
		chorbi.setSurname("1");
		chorbi.setEmail("prueba1@gmail.com");
		chorbi.setPhoneNumber("1234");
		chorbi.setPicture("http://kids.nationalgeographic.com/content/dam/kids/photos/articles/Other%20Explore%20Photos/H-P/International%20Photography%20Contest/IPC-winner-tile.jpg");
		chorbi.setDescription("Descripcion prueba 1");
		chorbi.setGenre(Genre.man);
		chorbi.setBirthDate(calendar.getTime());
		chorbi.setRelationshipEngage(RelationshipType.activities);
		chorbi.setCoordinates(coordinates);

		chorbi = this.chorbiService.save(chorbi);
		Assert.notNull(this.searchTemplateService.findByChorbiId(chorbi));

	}
	@Test
	public void testSave() {

		Chorbi chorbi;
		CreditCard creditCard;

		creditCard = new CreditCard();
		creditCard.setBrandName("MASTERCARD");
		creditCard.setHolderName("Holder name test");
		creditCard.setNumber("5379721258203853");
		creditCard.setExpirationMonth(7);
		creditCard.setExpirationYear(2017);
		creditCard.setCvv(159);

		chorbi = this.chorbiService.findOne(49);
		chorbi.setName("Nuevo nombre");
		chorbi.setCreditCard(creditCard);

		chorbi = this.chorbiService.save(chorbi);
		Assert.notNull(chorbi.getCreditCard());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeUnder18Save() {

		Chorbi chorbi;
		Calendar calendar;

		calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 17); // Hace 17 años

		chorbi = this.chorbiService.findOne(46);
		chorbi.setBirthDate(calendar.getTime());

		chorbi = this.chorbiService.save(chorbi);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeInvalidCreditCardBrandNameSave() {

		Chorbi chorbi;
		CreditCard creditCard;

		creditCard = new CreditCard();
		creditCard.setBrandName("Brand name erroneo");
		creditCard.setHolderName("Holder name test");
		creditCard.setNumber("5379721258203853");
		creditCard.setExpirationMonth(7);
		creditCard.setExpirationYear(2017);
		creditCard.setCvv(159);

		chorbi = this.chorbiService.findOne(46);
		chorbi.setCreditCard(creditCard);

		chorbi = this.chorbiService.save(chorbi);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeInvalidCreditCardExpirtationDateSave() {

		Chorbi chorbi;
		CreditCard creditCard;

		creditCard = new CreditCard();
		creditCard.setBrandName("MASTERCARD");
		creditCard.setHolderName("Holder name test");
		creditCard.setNumber("5379721258203853");
		creditCard.setExpirationMonth(3);
		creditCard.setExpirationYear(2017);
		creditCard.setCvv(159);

		chorbi = this.chorbiService.findOne(46);
		chorbi.setCreditCard(creditCard);

		chorbi = this.chorbiService.save(chorbi);

	}

	@Test
	public void testBan() {
		this.authenticate("admin");

		Chorbi chorbi;

		chorbi = this.chorbiService.findOne(49);

		chorbi = this.chorbiService.ban(chorbi);
		Assert.isTrue(chorbi.getBanned());

		this.unauthenticate();

	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeNotAuthenticatedBan() {
		Chorbi chorbi;

		chorbi = this.chorbiService.findOne(49);

		chorbi = this.chorbiService.ban(chorbi);
		Assert.isTrue(chorbi.getBanned());

	}

	@Test
	public void testUnban() {
		this.authenticate("admin");

		Chorbi chorbi;

		chorbi = this.chorbiService.findOne(49);

		chorbi = this.chorbiService.ban(chorbi);
		Assert.isTrue(chorbi.getBanned());

		chorbi = this.chorbiService.unban(chorbi);
		Assert.isTrue(!chorbi.getBanned());

		this.unauthenticate();

	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeNotAuthenticatedUnban() {
		Chorbi chorbi;

		chorbi = this.chorbiService.findOne(49);

		chorbi = this.chorbiService.unban(chorbi);
		Assert.isTrue(!chorbi.getBanned());

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
