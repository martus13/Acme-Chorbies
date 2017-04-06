
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChorbiRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Chirp;
import domain.Chorbi;
import domain.CreditCard;
import domain.Like;
import domain.SearchTemplate;
import forms.ChorbiForm;

@Service
@Transactional
public class ChorbiService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ChorbiRepository		chorbiRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private SearchTemplateService	searchTemplateService;

	@Autowired
	private AdministratorService	administratorService;


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
		Chorbi result;
		UserAccount userAccount;
		Authority authority;
		Collection<Like> givenLikes;
		Collection<Like> receivedLikes;
		Collection<Chirp> receivedChirps;
		Collection<Chirp> sentChirps;

		result = new Chorbi();

		userAccount = new UserAccount();
		authority = new Authority();
		givenLikes = new ArrayList<Like>();
		receivedLikes = new ArrayList<Like>();
		receivedChirps = new ArrayList<Chirp>();
		sentChirps = new ArrayList<Chirp>();

		authority.setAuthority("CHORBI");
		userAccount.getAuthorities().add(authority);

		result.setUserAccount(userAccount);
		result.setBanned(false);
		result.setGivenLikes(givenLikes);
		result.setReceivedLikes(receivedLikes);
		result.setReceivedChirps(receivedChirps);
		result.setSentChirps(sentChirps);

		return result;
	}
	public Chorbi save(final Chorbi chorbi) {
		Assert.notNull(chorbi);

		final Chorbi result;
		Calendar calendar;

		// comprobar que es mayor de edad:
		calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
		Assert.isTrue(chorbi.getBirthDate().before(calendar.getTime()) || chorbi.getBirthDate() == calendar.getTime());

		if (chorbi.getCreditCard() != null) {
			// si añade tarjeta de credito -> comprobar que es valida
			final CreditCard creditCard;
			final Calendar expirationCalendar;

			creditCard = chorbi.getCreditCard();
			calendar = Calendar.getInstance();
			expirationCalendar = Calendar.getInstance();

			// brand name
			Assert
				.isTrue(creditCard.getBrandName().equals("VISA") || creditCard.getBrandName().equals("MASTERCARD") || creditCard.getBrandName().equals("DISCOVER") || creditCard.getBrandName().equals("DINNERS") || creditCard.getBrandName().equals("AMEX"));

			// expiration date -> al menos un día más
			expirationCalendar.set(creditCard.getExpirationYear(), creditCard.getExpirationMonth() - 1, 1);
			expirationCalendar.set(Calendar.DAY_OF_MONTH, expirationCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1); // cogemos el último día del mes menos uno, porque tiene que ser un día más
			Assert.isTrue(calendar.before(expirationCalendar) || calendar.equals(expirationCalendar));
		}

		result = this.chorbiRepository.save(chorbi);

		if (chorbi.getId() == 0) {
			// crear searchTemplate:
			final SearchTemplate searchTemplate;

			searchTemplate = this.searchTemplateService.create(result);
			this.searchTemplateService.save(searchTemplate);
		}

		return result;
	}
	public Chorbi ban(Chorbi chorbi) {
		Assert.notNull(chorbi);

		Administrator administrator;

		administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);

		chorbi.setBanned(true);

		chorbi = this.chorbiRepository.save(chorbi);

		return chorbi;
	}

	public Chorbi unban(Chorbi chorbi) {
		Assert.notNull(chorbi);

		Administrator administrator;

		administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);

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

	public Object[] findMinMaxAvgAges() {
		Object[] results;

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

	public Chorbi reconstructCreate(final ChorbiForm chorbiForm) {
		Assert.notNull(chorbiForm);

		Chorbi chorbi;
		String password;

		Assert.isTrue(chorbiForm.getPassword().equals(chorbiForm.getConfirmPassword())); // Comprobamos que las dos contraseñas sean la misma
		Assert.isTrue(chorbiForm.getIsAgree()); // Comprobamos que acepte las condiciones

		chorbi = this.create();
		password = this.encryptPassword(chorbiForm.getPassword());

		chorbi.getUserAccount().setUsername(chorbiForm.getUsername());
		chorbi.getUserAccount().setPassword(password);
		chorbi.setName(chorbiForm.getName());
		chorbi.setSurname(chorbiForm.getSurname());
		chorbi.setEmail(chorbiForm.getEmail());
		chorbi.setPhoneNumber(chorbiForm.getPhoneNumber());
		chorbi.setPicture(chorbiForm.getPicture());
		chorbi.setDescription(chorbiForm.getDescription());
		chorbi.setRelationshipEngage(chorbiForm.getRelationshipEngage());
		chorbi.setGenre(chorbiForm.getGenre());
		chorbi.setCoordinates(chorbiForm.getCoordinates());
		chorbi.setBirthDate(chorbiForm.getBirthDate());

		return chorbi;
	}

	public ChorbiForm desreconstructCreate(final Chorbi chorbi) {
		ChorbiForm chorbiForm;

		chorbiForm = new ChorbiForm();

		chorbiForm.setUsername(chorbi.getUserAccount().getUsername());
		chorbiForm.setName(chorbi.getName());
		chorbiForm.setSurname(chorbi.getSurname());
		chorbiForm.setEmail(chorbi.getEmail());
		chorbiForm.setPhoneNumber(chorbi.getPhoneNumber());
		chorbiForm.setPicture(chorbi.getPicture());
		chorbiForm.setDescription(chorbi.getDescription());
		chorbiForm.setRelationshipEngage(chorbi.getRelationshipEngage());
		chorbiForm.setGenre(chorbi.getGenre());
		chorbiForm.setCoordinates(chorbi.getCoordinates());
		chorbiForm.setBirthDate(chorbi.getBirthDate());

		return chorbiForm;
	}

	public String encryptPassword(String password) {
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		password = encoder.encodePassword(password, null);

		return password;
	}
}
