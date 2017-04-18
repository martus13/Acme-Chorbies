
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Administrator findOne(final int adminId) {
		Assert.notNull(adminId);
		Assert.isTrue(adminId != 0);

		final Administrator result = this.administratorRepository.findOne(adminId);

		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();

		return result;
	}

	// Other business methods -------------------------------------------------

	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Administrator findByUserAccountId(final int userAccountId) {
		Assert.notNull(userAccountId);
		Assert.isTrue(userAccountId != 0);

		Administrator result;

		result = this.administratorRepository.findByUserAccountId(userAccountId);

		return result;
	}

}
