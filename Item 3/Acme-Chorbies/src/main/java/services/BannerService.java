
package services;

import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BannerRepository;
import domain.Administrator;
import domain.Banner;

@Service
@Transactional
public class BannerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private BannerRepository		bannerRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------
	public BannerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Banner findOne(final int bannerId) {
		Assert.isTrue(bannerId != 0);

		Banner result;

		result = this.bannerRepository.findOne(bannerId);

		return result;
	}

	public Collection<Banner> findAll() {
		Collection<Banner> result;

		result = this.bannerRepository.findAll();

		return result;
	}

	public Banner create() {
		Banner result;
		Administrator administrator;

		administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);

		result = new Banner();

		return result;
	}

	public Banner save(Banner banner) {
		Assert.notNull(banner);

		Administrator administrator;

		administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);

		banner = this.bannerRepository.save(banner);

		return banner;
	}

	public void delete(final Banner banner) {
		Administrator administrator;

		administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);

		this.bannerRepository.delete(banner);
	}

	// Other business methods -------------------------------------------------

	public Banner findRandom() {
		final Collection<Banner> collectionBanners;
		Banner[] banners;
		Banner result;

		collectionBanners = this.findAll();

		if (!collectionBanners.isEmpty()) {
			Random randomGenerator;

			randomGenerator = new Random();
			banners = collectionBanners.toArray(new Banner[collectionBanners.size()]);

			result = banners[randomGenerator.nextInt(collectionBanners.size())];
		} else
			result = null;

		return result;
	}
}
