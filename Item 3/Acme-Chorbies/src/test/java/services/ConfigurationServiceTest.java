
package services;

import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Configuration;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ConfigurationService	configurationService;


	// Tests ------------------------------------------------------------------

	// A continuación se van a realizar pruebas para comprobar el correcto funcionamiento de los casos de uso relacionados con Configuration.

	// Edición Configuration:
	@Test
	public void driverEditConfiguration() {

		Calendar correctCalendar;
		Calendar wrongCalendar;

		correctCalendar = Calendar.getInstance();
		correctCalendar.set(2017, 1, 1, 12, 0, 0);

		wrongCalendar = Calendar.getInstance();
		wrongCalendar.set(2017, 1, 1, 25, 120, 0);

		wrongCalendar = Calendar.getInstance();

		final Object testingData[][] = {
			{ // Bien
				"admin", correctCalendar.getTime(), null
			}, { // Error fecha
				"admin", wrongCalendar.getTime(), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testEdit((String) testingData[i][0], (Date) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void testEdit(final String username, final Date date, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			Configuration configuration;

			configuration = (Configuration) this.configurationService.findAll().toArray()[0];
			configuration.setCachedTime(date);

			configuration = this.configurationService.save(configuration);
			Assert.notNull(configuration);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	/////////////// Sin driver:

}
