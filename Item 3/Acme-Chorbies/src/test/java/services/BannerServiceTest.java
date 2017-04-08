
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Banner;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BannerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private BannerService	bannerService;


	// Tests ------------------------------------------------------------------

	// Aquí se van a realizar los tests necesarios para comprobar el correcto funcionamiento de la administración
	// de los banners que se mostrarán en la pantalla principal.

	// Creación y edición de un banner:
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{	// Bien
				"admin", "Pad-Thai prueba", "http://chefyan.ca/files/2014/07/pad-thai-Banner-1020-x-400-588x230.jpg", 43, null
			}, {// Error no autenticado
				null, "Pad-Thai prueba", "http://chefyan.ca/files/2014/07/pad-thai-Banner-1020-x-400-588x230.jpg", 43, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][4]);
			this.testEdit((String) testingData[i][0], (int) testingData[i][3], (String) testingData[i][2], (Class<?>) testingData[i][4]);
		}
	}

	// Eliminación de un banner:
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				"admin", 43, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDelete((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void testCreate(final String username, final String title, final String picture, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			Banner banner;

			banner = this.bannerService.create();
			banner.setTitle(title);
			banner.setPicture(picture);

			Assert.notNull(banner);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	protected void testEdit(final String username, final int bannerId, final String picture, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			Banner banner;

			banner = this.bannerService.findOne(bannerId);
			banner.setPicture(picture);

			banner = this.bannerService.save(banner);
			Assert.isTrue(banner.getPicture().equals(picture));

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	protected void testDelete(final String username, final int bannerId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			Banner banner;
			Collection<Banner> banners;

			banner = this.bannerService.findOne(bannerId);

			this.bannerService.delete(banner);

			banners = this.bannerService.findAll();
			Assert.isTrue(banners.size() == 2);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
