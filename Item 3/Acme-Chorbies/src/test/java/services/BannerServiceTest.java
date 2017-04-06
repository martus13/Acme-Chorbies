
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

	@Test
	public void testFindOne() {
		Banner banner;

		banner = this.bannerService.findOne(43);
		Assert.notNull(banner);
	}

	@Test
	public void testFindAll() {
		Collection<Banner> banners;

		banners = this.bannerService.findAll();
		Assert.isTrue(banners.size() == 3);
	}

	@Test
	public void testCreate() {
		this.authenticate("admin");

		Banner banner;

		banner = this.bannerService.create();
		banner.setTitle("Pad-Thai prueba");
		banner.setPicture("http://chefyan.ca/files/2014/07/pad-thai-Banner-1020-x-400-588x230.jpg");

		Assert.notNull(banner);

		this.unauthenticate();
	}

	@Test
	public void testSave() {
		this.authenticate("admin");

		Banner banner;

		banner = this.bannerService.findOne(43);
		banner.setPicture("http://chefyan.ca/files/2014/07/pad-thai-Banner-1020-x-400-588x230.jpg");

		banner = this.bannerService.save(banner);
		Assert.isTrue(banner.getPicture().equals("http://chefyan.ca/files/2014/07/pad-thai-Banner-1020-x-400-588x230.jpg"));

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeNotAuthenticatedSave() {
		Banner banner;

		banner = this.bannerService.findOne(43);
		banner.setPicture("http://chefyan.ca/files/2014/07/pad-thai-Banner-1020-x-400-588x230.jpg");

		banner = this.bannerService.save(banner);
		Assert.notNull(banner.equals("http://chefyan.ca/files/2014/07/pad-thai-Banner-1020-x-400-588x230.jpg"));

	}

	@Test
	public void testDelete() {
		this.authenticate("admin");

		Banner banner;
		Collection<Banner> banners;

		banner = this.bannerService.findOne(43);

		this.bannerService.delete(banner);

		banners = this.bannerService.findAll();
		Assert.isTrue(banners.size() == 2);

		this.unauthenticate();
	}
}
