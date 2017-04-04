
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChorbiRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Chorbi;
import domain.CreditCard;
import domain.Folder;
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

	// TODO: descomentar cuando se haga el servicio de folder
	//@Autowired
	//private FolderService			folderService;

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
		Collection<Folder> folders;

		result = new Chorbi();

		userAccount = new UserAccount();
		authority = new Authority();
		givenLikes = new ArrayList<Like>();
		receivedLikes = new ArrayList<Like>();
		folders = new ArrayList<Folder>();

		authority.setAuthority("CHORBI");
		userAccount.getAuthorities().add(authority);

		result.setUserAccount(userAccount);
		result.setBanned(false);
		result.setGivenLikes(givenLikes);
		result.setReceivedLikes(receivedLikes);
		result.setFolders(folders);

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

		//		// TODO: descomentar cuando se haga el servicio de folder
		//		if (chorbi.getFolders().isEmpty()) {
		//			// Folders
		//			Folder f1;
		//			Folder f2;
		//
		//			f1 = this.folderService.create(chorbi);
		//			f1.setName("inbox");
		//
		//			f2 = this.folderService.create(chorbi);
		//			f2.setName("outbox");
		//
		//			chorbi.addFolder(f1);
		//			chorbi.addFolder(f2);
		//
		//			result = this.chorbiRepository.save(chorbi);
		//
		//			f1.setChorbi(result);
		//			f2.setChorbi(result);
		//
		//			f1 = this.folderService.save(f1);
		//			f2 = this.folderService.save(f2);
		//		} else
		//			result = this.chorbiRepository.save(chorbi);

		result = this.chorbiRepository.save(chorbi); // TODO: quitar cuando se haga el servicio de Folder

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

}
