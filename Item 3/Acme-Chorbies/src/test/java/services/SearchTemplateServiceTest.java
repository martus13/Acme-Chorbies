
package services;

import java.util.ArrayList;
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
import domain.Genre;
import domain.RelationshipType;
import domain.SearchTemplate;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SearchTemplateServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private SearchTemplateService	searchTemplateService;

	@Autowired
	private ChorbiService			chorbiService;


	// Tests ------------------------------------------------------------------

	// A continuación se van a realizar pruebas para comprobar el correcto funcionamiento de los casos de uso relacionados con SearchTemplate.

	// Edición SearchTemplate:
	@Test
	public void driverEditSearchTemplate() {

		final Object testingData[][] = {
			{ // Bien
				"chorbi1", RelationshipType.activities, null, null, null, null, null, null, null, null, null
			}, { // Bien
				"chorbi1", null, null, null, null, null, null, null, null, "Sevilla", null
			}, { // Error usuario
				"admin", null, null, null, null, null, null, null, null, "Sevilla", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testEdit((String) testingData[i][0], (RelationshipType) testingData[i][1], (Integer) testingData[i][2], (String) testingData[i][3], (Genre) testingData[i][4], (Date) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);

	}

	// Test del método findChorbiesBySearchTemplate
	@Test
	public void driverFindChorbiesBySearchTemplate() {

		final Object testingData[][] = {
			{ 	 // Bien
				"chorbi1", null
			}, { // Bien
				"chorbi2", null
			}, { // Error: Usuario con tarjeta de crédito no válida
				"chorbi4", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testFindChorbiesBySearchTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	// Test del método findByChorbiId
	@Test
	public void driverFindByChorbiId() {

		final Object testingData[][] = {
			{ 	 // Bien
				"chorbi1", this.chorbiService.findByUserAccountId(39), null
			}, { // Bien
				"chorbi2", this.chorbiService.findByUserAccountId(40), null
			}, { // Error: El usuario no es un chorbi 
				"admin", this.chorbiService.findByUserAccountId(38), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testFindByChorbiId((String) testingData[i][0], (Chorbi) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	protected void testEdit(final String username, final RelationshipType relationshipType, final Integer approximateAge, final String singleKeyword, final Genre genre, final Date searchTime, final String country, final String state,
		final String province, final String city, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			SearchTemplate searchTemplate;

			searchTemplate = this.searchTemplateService.findByChorbiId(this.chorbiService.findByPrincipal());

			searchTemplate.setRelationshipType(relationshipType);
			searchTemplate.setApproximateAge(approximateAge);
			searchTemplate.setSingleKeyword(singleKeyword);
			searchTemplate.setGenre(genre);
			searchTemplate.setSearchTime(searchTime);
			searchTemplate.setCountry(country);
			searchTemplate.setState(state);
			searchTemplate.setProvince(province);
			searchTemplate.setCity(city);

			searchTemplate = this.searchTemplateService.save(searchTemplate);
			Assert.notNull(searchTemplate);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	protected void testFindChorbiesBySearchTemplate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);
			Chorbi chorbi;
			SearchTemplate searchTemplate;
			Collection<Chorbi> results;

			results = new ArrayList<Chorbi>();
			chorbi = this.chorbiService.findByPrincipal();
			searchTemplate = this.searchTemplateService.findByChorbiId(chorbi);

			results = this.searchTemplateService.findChorbiesBySearchTemplate(searchTemplate);
			Assert.notNull(results);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	protected void testFindByChorbiId(final String username, final Chorbi chorbi, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			SearchTemplate searchTemplate;

			searchTemplate = this.searchTemplateService.findByChorbiId(chorbi);

			Assert.notNull(searchTemplate);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// Sin driver ------------------------------

	@Test
	public void testFindAll() {
		Collection<SearchTemplate> searchTemplates;

		searchTemplates = this.searchTemplateService.findAll();
		Assert.isTrue(searchTemplates.size() == 5);
	}

}
