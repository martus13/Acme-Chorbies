
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChorbiRepository;
import security.LoginService;
import security.UserAccount;
import domain.Chirp;
import domain.Chorbi;
import domain.CreditCard;
import domain.Like;
import domain.SearchTemplate;

@Service
@Transactional
public class ChorbiService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ChorbiRepository	chorbiRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public ChorbiService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Chorbi findOne(final int chorbiId) {
		Assert.isTrue(chorbiId != 0);

		Chorbi result;

		result = this.chorbiRepository.findOne(chorbiId);

		return result;
	}

	public Collection<Chorbi> findAll() {
		Collection<Chorbi> result;

		result = this.chorbiRepository.findAll();

		return result;
	}

	public Chorbi create() {
		Chorbi chorbi;
		Collection<Like> givenLikes;
		Collection<Like> receivedLikes;
		Collection<Chirp> sentChirps;
		Collection<Chirp> receivedChirps;

		chorbi = new Chorbi();

		givenLikes = new ArrayList<Like>();
		receivedLikes = new ArrayList<Like>();
		sentChirps = new ArrayList<Chirp>();
		receivedChirps = new ArrayList<Chirp>();

		chorbi.setBanned(false);
		chorbi.setGivenLikes(givenLikes);
		chorbi.setReceivedLikes(receivedLikes);
		chorbi.setSentChirps(sentChirps);
		chorbi.setReceivedChirps(receivedChirps);

		return chorbi;
	}

	public Chorbi save(Chorbi chorbi) {
		Assert.notNull(chorbi);

		final SearchTemplate searchTemplate;
		long currentMilliseconds;
		long birthMilliseconds;
		Calendar calendar;

		// comprobar que es mayor de edad:
		calendar = Calendar.getInstance();
		currentMilliseconds = calendar.get(Calendar.MILLISECOND);

		calendar.setTime(chorbi.getBirthDate());
		birthMilliseconds = calendar.get(Calendar.MILLISECOND);

		calendar.setTimeInMillis(currentMilliseconds - birthMilliseconds);
		Assert.isTrue(calendar.get(Calendar.YEAR) >= 18); // TODO: comprobar!!

		if (chorbi.getCreditCard() != null) { // TODO: comprobar!!
			// si añade tarjeta de credito -> comprobar que es valida
			CreditCard creditCard;
			final String brandName;

			creditCard = chorbi.getCreditCard();
			brandName = creditCard.getBrandName();
			calendar = Calendar.getInstance();

			// brand name
			Assert.isTrue(brandName == "VISA" || brandName == "MASTERCARD" || brandName == "DISCOVER" || brandName == "DINNERS" || brandName == "AMEX");

			// expiration date -> al menos un día más
			Assert.isTrue(creditCard.getExpirationYear() >= calendar.get(Calendar.YEAR));
			if (creditCard.getExpirationYear() == calendar.get(Calendar.YEAR))
				Assert.isTrue(creditCard.getExpirationMonth() > (calendar.get(Calendar.MONTH) + 1));
		}

		// TODO: descomentar cuando se haga el servicio de searchTemplate
		// crear searchTemplate:
		// searchTemplate = searchTemplateService.create(chorbi);
		// searchTemplateService.save(searchTemplate);

		chorbi = this.chorbiRepository.save(chorbi);

		return chorbi;
	}
	public Chorbi ban(Chorbi chorbi) {
		Assert.notNull(chorbi);

		chorbi.setBanned(true);

		chorbi = this.chorbiRepository.save(chorbi);

		return chorbi;
	}

	public Chorbi unban(Chorbi chorbi) {
		Assert.notNull(chorbi);

		chorbi.setBanned(false);

		chorbi = this.chorbiRepository.save(chorbi);

		return chorbi;
	}

	// Other business methods -------------------------------------------------

	public Chorbi findByPrincipal() {
		Chorbi result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Chorbi findByUserAccountId(final int userAccountId) {
		Assert.notNull(userAccountId);

		Chorbi result;

		result = this.chorbiRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Collection<Chorbi> findNotBanned() {
		Collection<Chorbi> results;

		results = this.chorbiRepository.findNotBanned();

		return results;
	}

	public Collection<Chorbi> findGroupByCountry() {
		Collection<Chorbi> results;

		results = this.chorbiRepository.findGroupByCountry();

		return results;
	}

	public Collection<Chorbi> findGroupByCity() {
		Collection<Chorbi> results;

		results = this.chorbiRepository.findGroupByCity();

		return results;
	}

	public Long[] findMinMaxAvgAges() {
		Long[] results;

		results = this.chorbiRepository.findMinMaxAvgAges();

		return results;
	}

	public Collection<Chorbi> findAllSortedByReceivedLikes() {
		Collection<Chorbi> results;

		results = this.chorbiRepository.findAllSortedByReceivedLikes();

		return results;
	}

	public Double findRatioCreditCard() {
		Double result;
		Calendar calendar;

		calendar = Calendar.getInstance();
		result = this.chorbiRepository.findRatioCreditCard(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));

		return result;
	}

	public Double[] findRatioActivitiesLoveFriendship() {
		final Double[] result = new Double[3];

		result[0] = this.chorbiRepository.findRatioActivities();
		result[1] = this.chorbiRepository.findRatioLove();
		result[2] = this.chorbiRepository.findRatioFriendship();

		return result;
	}

}
