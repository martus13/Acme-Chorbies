
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

		Chorbi result;
		Calendar calendar;

		// comprobar que es mayor de edad:
		calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
		Assert.isTrue(chorbi.getBirthDate().before(calendar.getTime()) || chorbi.getBirthDate() == calendar.getTime());

		result = this.chorbiRepository.save(chorbi);

		if (chorbi.getId() == 0) {
			// crear searchTemplate:
			SearchTemplate searchTemplate;

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

	public Collection<Chorbi> findNotBannedBySearchTemplate(final SearchTemplate searchTemplate) {
		Collection<Chorbi> results;

		results = new ArrayList<Chorbi>();

		for (final Chorbi chorbi : this.findNotBanned()) {
			boolean aux;

			aux = true;

			if (searchTemplate.getSingleKeyword() != null)
				aux = aux
					&& (chorbi.getName().toLowerCase().contains(searchTemplate.getSingleKeyword().toLowerCase()) || chorbi.getSurname().toLowerCase().contains(searchTemplate.getSingleKeyword().toLowerCase()) || chorbi.getDescription().toLowerCase()
						.contains(searchTemplate.getSingleKeyword().toLowerCase()));

			if (searchTemplate.getRelationshipType() != null)
				aux = aux && (chorbi.getRelationshipEngage().equals(searchTemplate.getRelationshipType()));

			if (searchTemplate.getGenre() != null)
				aux = aux && (chorbi.getGenre().equals(searchTemplate.getGenre()));

			if (searchTemplate.getCountry() != null)
				aux = aux && (chorbi.getCoordinates().getCountry().toLowerCase().contains(searchTemplate.getCountry().toLowerCase()));

			if (searchTemplate.getCity() != null)
				aux = aux && (chorbi.getCoordinates().getCity().toLowerCase().contains(searchTemplate.getCity().toLowerCase()));

			if (searchTemplate.getProvince() != null)
				aux = aux && (searchTemplate.getProvince() != null && chorbi.getCoordinates().getProvice() != null && chorbi.getCoordinates().getProvice().toLowerCase().contains(searchTemplate.getProvince().toLowerCase()));

			if (searchTemplate.getState() != null)
				aux = aux && (searchTemplate.getState() != null && chorbi.getCoordinates().getState() != null && chorbi.getCoordinates().getState().toLowerCase().contains(searchTemplate.getState().toLowerCase()));

			if (searchTemplate.getApproximateAge() != null) {
				Calendar calendar;
				final Calendar calendarMin;
				final Calendar calendarMax;

				calendar = Calendar.getInstance();
				calendarMin = Calendar.getInstance();
				calendarMax = Calendar.getInstance();
				calendar.add(Calendar.YEAR, -searchTemplate.getApproximateAge());

				calendarMin.setTime(calendar.getTime());
				calendarMin.add(Calendar.YEAR, -5);

				calendarMax.setTime(calendar.getTime());
				calendarMax.add(Calendar.YEAR, 5);

				aux = aux && (!(chorbi.getBirthDate().before(calendarMin.getTime()) || chorbi.getBirthDate().after(calendarMax.getTime())));
			}

			if (aux)
				results.add(chorbi);
		}

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

	public Chorbi reconstructEditProfile(final ChorbiForm chorbiForm) {
		Assert.notNull(chorbiForm);

		Chorbi chorbi;
		String password;

		Assert.isTrue(chorbiForm.getPassword().equals(chorbiForm.getConfirmPassword())); // Comprobamos que las dos contraseñas sean la misma

		chorbi = this.findByPrincipal();
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

	public String encryptPassword(String password) {
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		password = encoder.encodePassword(password, null);

		return password;
	}

}
