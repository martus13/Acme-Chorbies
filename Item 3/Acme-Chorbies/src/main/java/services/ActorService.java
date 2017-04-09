
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Chorbi;
import forms.CreateChorbiForm;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ActorRepository	actorRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Actor findOne(final int actorId) {
		Actor result;

		result = this.actorRepository.findOne(actorId);

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();

		return result;
	}

	public Actor save(Actor actor) {

		if (actor.getId() == 0) {
			String password;
			String encryptedPassword;

			password = actor.getUserAccount().getPassword();
			encryptedPassword = this.encryptPassword(password);

			actor.getUserAccount().setPassword(encryptedPassword);

		}
		actor = this.actorRepository.save(actor);

		return actor;
	}

	// Other business methods -------------------------------------------------

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Actor findByUserAccountId(final int userAccountId) {
		Assert.notNull(userAccountId);

		Actor result;

		result = this.actorRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Boolean checkAuthority(final Actor actor, final String authority) {
		Boolean result;
		Collection<Authority> authorities;

		authorities = actor.getUserAccount().getAuthorities();

		result = false;
		for (final Authority a : authorities)
			result = result || (a.getAuthority().equals(authority));

		return result;
	}

	public Actor reconstructEditProfile(final CreateChorbiForm chorbiForm) {
		Assert.notNull(chorbiForm);

		Actor actor;
		String password;

		Assert.isTrue(chorbiForm.getPassword().equals(chorbiForm.getConfirmPassword())); // Comprobamos que las dos contraseñas sean la misma

		actor = this.findByPrincipal();
		password = this.encryptPassword(chorbiForm.getPassword());

		actor.getUserAccount().setUsername(chorbiForm.getUsername());
		actor.getUserAccount().setPassword(password);
		actor.setName(chorbiForm.getName());
		actor.setSurname(chorbiForm.getSurname());
		actor.setEmail(chorbiForm.getEmail());
		actor.setPhoneNumber(chorbiForm.getPhoneNumber());

		if (this.checkAuthority(actor, "CHORBI")) {
			Chorbi chorbi;

			chorbi = (Chorbi) actor;

			chorbi.setPicture(chorbiForm.getPicture());
			chorbi.setDescription(chorbiForm.getDescription());
			chorbi.setRelationshipEngage(chorbiForm.getRelationshipEngage());
			chorbi.setGenre(chorbiForm.getGenre());
			chorbi.setCoordinates(chorbiForm.getCoordinates());
			chorbi.setBirthDate(chorbiForm.getBirthDate());

			actor = chorbi;
		}

		return actor;
	}

	public CreateChorbiForm desreconstructEditProfile(final Actor actor) {
		CreateChorbiForm chorbiForm;

		chorbiForm = new CreateChorbiForm();

		chorbiForm.setUsername(actor.getUserAccount().getUsername());
		chorbiForm.setName(actor.getName());
		chorbiForm.setSurname(actor.getSurname());
		chorbiForm.setEmail(actor.getEmail());
		chorbiForm.setPhoneNumber(actor.getPhoneNumber());

		if (this.checkAuthority(actor, "CHORBI")) {
			Chorbi chorbi;

			chorbi = (Chorbi) actor;

			chorbiForm.setPicture(chorbi.getPicture());
			chorbiForm.setDescription(chorbi.getDescription());
			chorbiForm.setRelationshipEngage(chorbi.getRelationshipEngage());
			chorbiForm.setGenre(chorbi.getGenre());
			chorbiForm.setCoordinates(chorbi.getCoordinates());
			chorbiForm.setBirthDate(chorbi.getBirthDate());
		}

		return chorbiForm;
	}

	public String encryptPassword(String password) {
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		password = encoder.encodePassword(password, null);

		return password;
	}

}
