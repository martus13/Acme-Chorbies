
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
import domain.Chorbi;
import domain.CreditCard;
import domain.Like;
import domain.SearchTemplate;

@Service
@Transactional
public class ChorbiService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ChorbiRepository		chorbiRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private SearchTemplateService	searchTemplateService;


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

		chorbi = new Chorbi();

		givenLikes = new ArrayList<Like>();
		receivedLikes = new ArrayList<Like>();

		chorbi.setBanned(false);
		chorbi.setGivenLikes(givenLikes);
		chorbi.setReceivedLikes(receivedLikes);

		return chorbi;
	}

	public Chorbi save(final Chorbi chorbi) {
		Assert.notNull(chorbi);

		final Chorbi result;
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
			final CreditCard creditCard;
			final String brandName;
			final Calendar expirationCalendar;

			creditCard = chorbi.getCreditCard();
			brandName = creditCard.getBrandName();
			calendar = Calendar.getInstance();
			expirationCalendar = Calendar.getInstance();

			// brand name
			Assert.isTrue(brandName == "VISA" || brandName == "MASTERCARD" || brandName == "DISCOVER" || brandName == "DINNERS" || brandName == "AMEX");

			// expiration date -> al menos un día más
			expirationCalendar.set(creditCard.getExpirationYear(), creditCard.getExpirationMonth() - 1, 1);
			expirationCalendar.set(Calendar.DAY_OF_MONTH, expirationCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1); // cogemos el último día del mes menos uno, porque tiene que ser un día más
			Assert.isTrue(calendar.before(expirationCalendar) || calendar.equals(expirationCalendar));
		}

		result = this.chorbiRepository.save(chorbi);

		if (chorbi.getId() == 0) { // TODO: comprobar
			// crear searchTemplate:
			final SearchTemplate searchTemplate;

			searchTemplate = this.searchTemplateService.create(result);
			this.searchTemplateService.save(searchTemplate);
		}

		return result;
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

	public Collection<Object[]> findGroupByCountryAndCity() {
		Collection<Object[]> results;

		results = this.chorbiRepository.findGroupByCountryAndCity();

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
